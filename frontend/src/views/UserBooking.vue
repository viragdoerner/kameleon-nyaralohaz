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
            ><v-btn text class="overline cgreen--text" to="/booking"
              >ezen a linken</v-btn
            ></span
          >
          tudsz
        </div>
        <user-booking-exp-panels
          :bookings="active_bookings"
          :active="true"
          v-on:activeStateChanged="addToInactive"
        ></user-booking-exp-panels>
      </v-card-text>
      <v-card-title
        v-if="inactive_bookings.length > 0"
        class="cgreen--text text-h4"
      >
        Lejárt foglalások
      </v-card-title>
      <v-card-text>
        <user-booking-exp-panels
          :bookings="inactive_bookings"
          :active="false"
          v-on:activeStateChanged="addToActive"
        ></user-booking-exp-panels>
      </v-card-text>
    </v-card>
  </v-container>
</template>

<script>
import ApiService from "../services/api.service";
import BookingDataService from "../services/bookingData.service";
import UserBookingExpPanels from "../components/booking/UserBookingExpPanels.vue";

export default {
  name: "CUserBooking",
  components: { UserBookingExpPanels },
  data: () => ({
    active_bookings: [],
    inactive_bookings: [],
  }),

  created() {
    this.initialize();
  },
  methods: {
    addToInactive(b) {
      console.log(b);
      this.active_bookings = this.active_bookings.filter(function (booking) {
        return booking.id !== b.id;
      });
      this.inactive_bookings.push(b);
    },
    addToActive(b) {
      this.inactive_bookings = this.inactive_bookings.filter(function (booking) {
        return booking.id !== b.id;
      });
      this.active_bookings.push(b);
    },
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
  },
};
</script>
