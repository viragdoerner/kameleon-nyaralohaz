<template>
  <div>
    <v-card :color="event.color" dark>
      <v-card-title> {{ event.name }}</v-card-title>
      <v-card-subtitle
        ><span>{{ booking.user.email }}</span
        >,
        <span class="pl-2">{{
          booking.user.phonenumber
        }}</span></v-card-subtitle
      >
    </v-card>
    <v-card-text>
      <div class="d-flex justify-space-between mb-3">
        <v-chip v-if="booking.dogIncluded" color="pink" text-color="white">
          <v-icon> mdi-paw </v-icon>
        </v-chip>
        <v-chip
          :color="statusAttrs(booking.status, booking).color"
          text-color="white"
        >
          <v-icon left>
            {{ statusAttrs(booking.status, booking)["icon"] }}
          </v-icon>

          {{ statusAttrs(booking.status, booking).status_admin }}
        </v-chip>
        <v-chip :color="event.color" text-color="white">
          {{ booking.apartment.name }}
        </v-chip>
      </div>
      <div class="d-flex flex-column mb-3" v-if="booking.transitions[0].comment">
        <v-alert text color="darkgrey">
          <div class="d-flex flex-column">
            <p>"{{ booking.transitions[0].comment }}"</p>
            <div class="align-self-end overline">
              - {{ booking.user.firstname }},
              {{ formatDate(booking.transitions[0].created) }}
            </div>
          </div>
        </v-alert>
      </div>
      <v-row class="d-flex justify-center my-3"
        ><v-chip color="cgreen" text-color="white">
          {{ formatDate(booking.arrival) }} -
          {{ formatDate(booking.departure) }}</v-chip
        ></v-row
      >
    </v-card-text>
  </div>
</template>

<script>
import BookingDataService from "../../services/bookingData.service";
import BookingService from "../../services/booking.service";
import MomentService from "../../services/moment.service";

export default {
  name: "CBookingDetails",
  props: ["event"],
  components: {},
  data: () => ({}),
  methods: {
    getTotalPrice(booking) {
      return BookingService.getTotalPriceForBooking(
        booking.arrival,
        booking.departure,
        booking.apartment.price
      );
    },
    formatDate(d) {
      return MomentService.formatDate(d);
    },
    formatDateTime(d) {
      return MomentService.formatDateTime(d);
    },
    statusAttrs(status, booking) {
      return BookingDataService.bookingStatusAttrsForUser(status, booking);
    },
  },
  computed: {
    booking() {
      return this.event.booking;
    },
  },
  mounted() {},
};
</script>
