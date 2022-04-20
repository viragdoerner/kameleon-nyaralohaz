<template>
  <v-container class="d-flex justify-center pb-10">
    <v-card class="col-12">
      <v-card-title class="cgreen--text text-h4">
        Aktív foglalásaid
      </v-card-title>
      <v-card-text>
        <div v-if="active_bookings.length < 1">
          Jelenleg nincs foglalásod. Foglalni
          <span
            ><v-btn text class="overline cgreen--text" href="/booking"
              >ezen a linken</v-btn
            ></span
          >
          tudsz
        </div>
        <v-expansion-panels multiple>
          <v-expansion-panel v-for="(item, i) in active_bookings" :key="i">
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
            <v-expansion-panel-content class="d-flex flex-column">
              <booking-tabs :booking="item"></booking-tabs>
              <v-tooltip bottom>
                <template v-slot:activator="{ on, attrs }">
                  <v-btn
                    v-bind="attrs"
                    v-on="on"
                    @click="openDialog(item)"
                    elevation="2"
                    small
                    color="white"
                    class="mt-auto align-self-end"
                    ><v-icon left color="red"> fa-ban </v-icon>FOGLALÁS
                    LEMONDÁSA
                  </v-btn>
                </template>
                <span class="text-caption"
                  ><p class="mb-0">Meglévő foglalást bármikor lemondhatsz,</p>
                  de a befizetett foglaló nem kerül visszafizetésre.</span
                >
              </v-tooltip>
            </v-expansion-panel-content>
          </v-expansion-panel>
        </v-expansion-panels>
      </v-card-text>
      <v-card-title
        v-if="inactive_bookings.length > 0"
        class="cgreen--text text-h4"
      >
        Lejárt foglalások
      </v-card-title>
      <v-card-text>
        <v-expansion-panels multiple>
          <v-expansion-panel v-for="(item, i) in inactive_bookings" :key="i">
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
            <v-expansion-panel-content class="d-flex flex-column">
              <booking-tabs :booking="item"></booking-tabs>
              <v-tooltip bottom>
                <template v-slot:activator="{ on, attrs }">
                  <v-btn
                    v-bind="attrs"
                    v-on="on"
                    @click="openDialog(item)"
                    elevation="2"
                    small
                    color="white"
                    class="mt-auto align-self-end"
                    ><v-icon left color="red"> fa-ban </v-icon>FOGLALÁS
                    LEMONDÁSA
                  </v-btn>
                </template>
                <span class="text-caption"
                  ><p class="mb-0">Meglévő foglalást bármikor lemondhatsz,</p>
                  de a befizetett foglaló nem kerül visszafizetésre.</span
                >
              </v-tooltip>
            </v-expansion-panel-content>
          </v-expansion-panel>
        </v-expansion-panels>
      </v-card-text>
    </v-card>
    
    <confirm-dialog
      :confirmDialog="confirmDialog"
      v-on:confirm="cancelBooking()"
    ></confirm-dialog>
  </v-container>
</template>

<script>
import ApiService from "../services/api.service";
import BookingDataService from "../services/bookingData.service";
import BookingTabs from "../components/booking/BookingTabs.vue";
import bookingService from "../services/booking.service";
import ConfirmDialog from "../components/ConfirmDialog.vue";

export default {
  name: "CUserBooking",
  components: { BookingTabs, ConfirmDialog },
  data: () => ({
    active_bookings: [],
    inactive_bookings: [],
    confirmDialog: {
      isOpen: false,
      text: "Biztosan le szeretnéd mondani a foglalást? Amennyiben már kifizetted a foglalót az nem jár vissza.",
      confirmButton: "Törlés",
    },
    bookingToBeRemoved: {},
  }),

  created() {
    this.initialize();
  },

  mounted() {},
  computed: {},
  methods: {
    initialize() {
      ApiService.GET("booking/user")
        .then((response) => {
          this.active_bookings = response.data.active;
          this.inactive_bookings = response.data.inactive;
        })
        .catch((error) => {
          this.$store.commit("showMessage", {
            active: true,
            color: "error",
            message: "Hiba történt a foglalások adatainak lekérésénél",
          });
        });
    },
    statusAttrs(status, booking) {
      return BookingDataService.bookingStatusAttrsForUser(status, booking);
    },
    openDialog(item) {
      console.log(item);
      this.confirmDialog.isOpen = true;
      this.bookingToBeRemoved = item;
      console.log(this.bookingToBeRemoved);
    },
    cancelBooking() {
      console.log(this.bookingToBeRemoved);
      ApiService.PUT("booking/cancel/" + this.bookingToBeRemoved.id, )
        .then((response) => {
          this.active_bookings = this.active_bookings.filter(function (b) {
            return b.id !== bookingService.id;
          });
          this.inactive_bookings = this.active_bookings.filter(function (b) {
            return b.id !== b.id;
          });
          this.$store.commit("showMessage", {
            active: true,
            color: "success", // You can create another actions for diferent color.
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
