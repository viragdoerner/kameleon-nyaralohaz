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
            <v-expansion-panel-content>
              <booking-tabs :booking="item"></booking-tabs>
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
            <v-expansion-panel-content>
              <booking-tabs :booking="item"></booking-tabs>
            </v-expansion-panel-content>
          </v-expansion-panel>
        </v-expansion-panels>
      </v-card-text>
    </v-card>
  </v-container>
</template>

<script>
import ApiService from "../services/api.service";
import BookingDataService from "../services/bookingData.service";
import BookingTabs from "../components/booking/BookingTabs.vue";

export default {
  name: "CUserBooking",
  components: { BookingTabs },
  data: () => ({
    dialogCancel: false,
    active_bookings: [],
    inactive_bookings: [],
    editedIndex: -1,
    editedItem: {
      email: "",
      lastname: 0,
      firstname: 0,
      phonenumber: 0,
    },
  }),

  watch: {
    dialogCancel(val) {
      val || this.closeCancel();
    },
  },

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
    cancelBooking(item) {
      this.editedIndex = this.bookings.indexOf(item);
      this.editedItem = Object.assign({}, item);
      this.dialogCancel = true;
    },

    cancelBookingConfirm() {
      this.closeCancel();
      ApiService.PUT("booking/cancel/" + this.editedItem.id)
        .then((response) => {
          this.users.splice(this.editedIndex, 1);
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

    closeCancel() {
      this.dialogCancel = false;
      this.$nextTick(() => {
        this.editedItem = Object.assign({}, this.defaultItem);
        this.editedIndex = -1;
      });
    },
  },
};
</script>
