<template>
  <v-container class="d-flex justify-center pb-10 flex-column">
    <div class="cgreen--text text-h3 py-3 pt-5">Összes foglalás</div>
    <v-tabs
      v-model="tab"
      background-color="cbggreen"
      color="cgreen"
      center-active
      icons-and-text
    >
      <v-tabs-slider color="yellow"></v-tabs-slider>

      <v-tab> Naptár nézet <v-icon>mdi-eye</v-icon></v-tab>
      <v-tab> Lista nézet <v-icon>mdi-pencil</v-icon></v-tab>
      <v-tab> Törölt foglalások <v-icon>mdi-delete</v-icon></v-tab>
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
  components: { AdminBookingTable, AdminBookingCalendar },
  data: () => ({
    bookings: [],
    tab: null,
  }),

  created() {
    this.initialize();
  },
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
  methods: {
    statusChanged(changedBooking) {
      for (var i = 0; i < this.bookings.length; i++) {
        if (this.bookings[i].id == changedBooking.id) {
          //this.bookings[i] = changedBooking;
          this.$set(this.bookings, i, changedBooking);
        }
      }
    },
    initialize() {
      ApiService.GET("booking")
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
