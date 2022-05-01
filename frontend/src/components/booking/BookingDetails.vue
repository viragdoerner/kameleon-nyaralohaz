<template>
  <div>
    <v-toolbar :color="event.color" dark>
      <v-toolbar-title v-html="event.name"></v-toolbar-title>
    </v-toolbar>
    <v-card-text>
      <v-simple-table>
        <template v-slot:default>
          <tbody>
            <tr>
              <td>Állapot</td>
              <td>{{ statusAttrs(booking.status, booking).status_admin }}</td>
            </tr>
            <tr v-if="booking.transitions">
              <td>Megjegyzés</td>
              <td>{{ booking.transitions[0].comment }}</td>
            </tr>
            <tr v-if="booking.transitions">
              <td>Foglalás létrehozása</td>
              <td>{{ formatDate(booking.transitions[0].created) }}</td>
            </tr>
            <tr v-if="booking.transitions">
              <td>Utoljára módosítva</td>
              <td>
                {{
                  formatDate(
                    booking.transitions[booking.transitions.length - 1].created
                  )
                }}
              </td>
            </tr>
            <tr>
              <td>Kutyussal jövünk</td>
              <td>{{ booking.dogIncluded ? "Igen" : "Nem" }}</td>
            </tr>
            <tr>
              <td>Lefoglalt apartman</td>
              <td>{{ booking.apartment.name }}</td>
            </tr>
            <tr>
              <td>Foglalás teljes ára</td>
              <td>{{ getTotalPrice(booking) }} Ft</td>
            </tr>
            <tr>
              <td>Email</td>
              <td>{{ booking.user.email }} Ft</td>
            </tr>
            <tr>
              <td>Telefonszám</td>
              <td>booking.user.phonenumber</td>
            </tr>
          </tbody>
        </template>
      </v-simple-table>
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
  data: () => ({
  }),
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
    booking(){
      return this.event.booking;
    }
  },
  mounted() {
  },
};
</script>
