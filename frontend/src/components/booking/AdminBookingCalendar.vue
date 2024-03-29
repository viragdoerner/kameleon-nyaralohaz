<template>
  <v-row class="fill-height">
    <v-col>
      <v-sheet height="64">
        <v-toolbar flat>
          <v-btn outlined class="mr-4" color="grey darken-2" @click="setToday">
            Ma
          </v-btn>
          <v-btn fab text small color="grey darken-2" @click="prev">
            <v-icon small> mdi-chevron-left </v-icon>
          </v-btn>
          <v-btn fab text small color="grey darken-2" @click="next">
            <v-icon small> mdi-chevron-right </v-icon>
          </v-btn>
          <v-toolbar-title v-if="$refs.calendar">
            {{ $refs.calendar.title }}
          </v-toolbar-title>
          <v-spacer></v-spacer>
        </v-toolbar>
      </v-sheet>
      <v-sheet height="600">
        <v-calendar
          ref="calendar"
          v-model="focus"
          color="primary"
          :events="events"
          :event-color="getEventColor"
          type="month"
          @click:event="showEvent"
          ><template v-slot:event="{ event }">
            <v-icon class="pl-1" small color="white">
              {{ statusAttrs(event.booking.status, event.booking)["icon"] }}
            </v-icon>
            <span class="pl-2">{{ event.name }}</span>
            <v-icon
              v-if="event.booking.dogIncluded"
              color="white"
              class="pl-1"
              small
              >mdi-paw</v-icon
            >
          </template>
        </v-calendar>
        <v-dialog
          v-if="!$vuetify.breakpoint.smAndUp"
          v-model="selectedOpen"
          :close-on-content-click="false"
          :activator="selectedElement"
        >
          <v-card color="grey lighten-4" min-width="350px" flat>
            <booking-details :event="selectedEvent"></booking-details>
          </v-card>
        </v-dialog>
        <v-menu
          v-if="$vuetify.breakpoint.smAndUp"
          v-model="selectedOpen"
          :close-on-content-click="false"
          :activator="selectedElement"
          offset-x
        >
          <v-card color="grey lighten-4" min-width="350px" flat>
            <booking-details :event="selectedEvent"></booking-details>
          </v-card>
        </v-menu>
      </v-sheet>
    </v-col>
  </v-row>
</template>

<script>
import BookingService from "../../services/booking.service";
import BookingDetails from "./BookingDetails.vue";
import BookingDataService from "../../services/bookingData.service";
export default {
  name: "CAdminBookingCalendar",
  components: { BookingService, BookingDetails },
  props: ["bookings"],
  data: () => ({
    focus: "",
    selectedEvent: {},
    selectedElement: null,
    selectedOpen: false,
    events: [],
    apartmentColors: {},
  }),
  mounted() {},
  watch: {
    bookings: function (newVal, oldVal) {
      this.apartmentColors = BookingService.getApartmentsWithColor(
        this.bookings
      );
      const events = BookingService.convertBookingsToEvents(this.bookings);
      this.events = events;
    },
  },
  methods: {
    getEventColor(event) {
      return event.color;
    },
    setToday() {
      this.focus = "";
    },
    prev() {
      this.$refs.calendar.prev();
    },
    next() {
      this.$refs.calendar.next();
    },
    showEvent({ nativeEvent, event }) {
      const open = () => {
        this.selectedEvent = event;
        this.selectedElement = nativeEvent.target;
        requestAnimationFrame(() =>
          requestAnimationFrame(() => (this.selectedOpen = true))
        );
      };

      if (this.selectedOpen) {
        this.selectedOpen = false;
        requestAnimationFrame(() => requestAnimationFrame(() => open()));
      } else {
        open();
      }

      nativeEvent.stopPropagation();
    },
    rnd(a, b) {
      return Math.floor((b - a + 1) * Math.random()) + a;
    },
    statusAttrs(status, booking) {
      return BookingDataService.bookingStatusAttrsForUser(status, booking);
    },
  },
};
</script>
