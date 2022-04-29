<template>
  <v-container class="d-flex justify-center pb-10 flex-column">
    <div class="cgreen--text text-h3 py-3 pt-5">Összes foglalás</div>
    <v-tabs
      v-model="tab"
      background-color="cbggreen"
      color="cgreen"
      center-active
    >
      <v-tabs-slider color="yellow"></v-tabs-slider>

      <v-tab> Naptár nézet</v-tab>
      <v-tab> Lista nézet</v-tab>
      <v-tab> Törölt foglalások</v-tab>
    </v-tabs>

    <v-card class="col-12">
      <v-tabs-items v-model="tab">
        <v-tab-item>
          <admin-booking-calendar
            :bookings="active_bookings"
          ></admin-booking-calendar>
        </v-tab-item>
        <v-tab-item>
          <div v-if="active_bookings.length < 1">
            Jelenleg nincs egy foglalás sem.
          </div>
          <div v-else>
            <admin-booking-table
              :bookings="active_bookings"
              :active="true"
              v-on:statusChanged="statusChanged"
              key="adminbookingactive"
            ></admin-booking-table>
          </div>
        </v-tab-item>
        <v-tab-item>
          <div v-if="inactive_bookings.length < 1">
            Jelenleg nincs egy lejárt/törölt foglalás sem.
          </div>
          <div v-else>
            <admin-booking-table
              :bookings="inactive_bookings"
              :active="false"
              v-on:statusChanged="statusChanged"
              key="adminbookinginactive"
            ></admin-booking-table>
          </div>
        </v-tab-item>
      </v-tabs-items>
    </v-card>
  </v-container>
</template>

<script>
import ApiService from "../services/api.service";
import BookingDataService from "../services/bookingData.service";
import AdminBookingTable from "../components/booking/AdminBookingTable.vue";
import AdminBookingCalendar from "../components/booking/AdminBookingCalendar.vue";

export default {
  name: "CAdminBooking",
  components: { AdminBookingTable,AdminBookingCalendar },
  data: () => ({
    active_bookings: [],
    inactive_bookings: [],
    tab: null,
  }),

  created() {
    this.initialize();
  },
  methods: {
    statusChanged(b) {
      this.active_bookings = this.active_bookings.filter(function (booking) {
        return booking.id !== b.id;
      });
      this.inactive_bookings = this.inactive_bookings.filter(function (
        booking
      ) {
        return booking.id !== b.id;
      });
      if (b.active) {
        this.active_bookings.push(b);
      } else {
        this.inactive_bookings.push(b);
      }
    },
    initialize() {
      ApiService.GET("booking")
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
