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
          v-on:statusChanged="statusChanged"
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
          v-on:statusChanged="statusChanged"
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
    bookings: [],
  }),
  computed: {
    active_bookings() {
      return this.bookings.filter(function (booking) {
        return booking.active;
      });
    },
    inactive_bookings() {
      return this.bookings.filter(function (booking) {
        return !booking.active;
      });
    },
  },
  created() {
    this.initialize();
  },
  methods: {
    statusChanged(changedBooking) {
      for (var i = 0; i < this.bookings.length; i++) {
        if (this.bookings[i].id == changedBooking.id) {
          this.$set(this.bookings, i, changedBooking);
        }
      }
    },
    initialize() {
      ApiService.GET("booking/user")
        .then((response) => {
          this.bookings = response.data.active.concat(response.data.inactive);
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
