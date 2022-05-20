<template>
  <div>
    <v-expansion-panels multiple>
      <v-expansion-panel v-for="booking in bookings" :key="booking.id" transition="scale-transition">
        <v-expansion-panel-header>
          <template>
            <v-row no-gutters>
              <v-col cols="1">
                <v-icon :color="statusAttrs(item.status, item).color">
                  {{ statusAttrs(item.status, item)["icon"] }}
                </v-icon>
              </v-col>
              <v-col cols="4" class="d-flex align-center">
                <div class="overline">
                  {{ statusAttrs(item.status, item).status }}
                </div>
              </v-col>
            </v-row>
          </template>
        </v-expansion-panel-header>
        <v-expansion-panel-content>
          <div class="d-flex flex-column">
            <booking-tabs :booking="item"></booking-tabs>
            <v-tooltip bottom>
              <template v-slot:activator="{ on, attrs }">
                <v-btn
                  v-if="active"
                  v-bind="attrs"
                  v-on="on"
                  @click="openDialog(item)"
                  elevation="2"
                  small
                  color="white"
                  class="mt-auto align-self-end"
                  ><v-icon left color="red"> fa-ban </v-icon>FOGLALÁS LEMONDÁSA
                </v-btn>
              </template>
              <span class="text-caption"
                ><p class="mb-0">Meglévő foglalást bármikor lemondhatsz,</p>
                de a befizetett foglaló nem kerül visszafizetésre.</span
              >
            </v-tooltip>
          </div>
          <confirm-dialog v-on:confirm="cancelBooking(booking, arguments[0])"></confirm-dialog>
        </v-expansion-panel-content>
      </v-expansion-panel>
    </v-expansion-panels>
  </div>
</template>

<script>
import ApiService from "../../services/api.service";
import BookingDataService from "../../services/bookingData.service";
import BookingTabs from "./BookingTabs.vue";
import ConfirmDialog from "../ConfirmDialog.vue";

export default {
  name: "CUserBookingExpPanels",
  components: { BookingTabs, ConfirmDialog },
  props: ["bookings", "active"],
  data: () => ({
  }),
  mounted() {},
  computed: {},
  methods: {
    statusAttrs(status, booking) {
      return BookingDataService.bookingStatusAttrsForUser(status, booking);
    },
    openDialog(booking) {
      
      this.$store.commit("dialog/openDialogWithForm", {
        title: "Foglalás lemondása",
        text: "Biztosan le szeretnéd mondani a foglalást? Amennyiben már kifizetted a foglalót az nem jár vissza.",
        confirmButton: "OK",
        form: {
          textfieldLabel:
            "Kérjük indokold meg, hogy mi miatt mondod le a foglalást!",
        },
      });
    },
    cancelBooking(booking, payload) {
     
      ApiService.PUT("booking/cancel/" + booking.id, payload)
        .then((response) => {
          this.$emit("statusChanged", response.data);
          this.$store.commit("showMessage", {
            active: true,
            color: "success", 
            message: "Foglalás sikeresen lemondva",
          });
        })
        .catch((error) => {
          this.$store.commit("showMessage", {
            active: true,
            color: "error",
            message:
              "Nem sikerült lemondani a foglalást. Próbáld újra, vagy mondd le telefonon vagy emailben!",
          });
        });
    },
  },
};
</script>
