<template>
  <div>
    <v-tabs :vertical="!$vuetify.breakpoint.mobile" color="cgreen">

        <v-tab class="d-flex justify-sm-start caption" v-if="!admin">
          <v-icon small left> mdi-message-reply-text </v-icon>
          {{ !$vuetify.breakpoint.smAndUp ? "" : "Leírás" }}
        </v-tab>
        <v-tab class="d-flex  justify-sm-start caption">
          <v-icon small left> mdi-information </v-icon>
          {{ !$vuetify.breakpoint.smAndUp ? "" : "Részletek" }}
        </v-tab>
        <v-tab class="d-flex  justify-sm-start caption">
          <v-icon small left> mdi-history </v-icon>
          {{ !$vuetify.breakpoint.smAndUp ? "" : "Történet" }}
        </v-tab>

      <v-tab-item v-if="!admin">
        <v-card flat>
          <v-card-text class="pa-0 pa-sm-4 pt-1">
            <p v-if="!admin">
              {{ statusAttrs(booking.status, booking).info }}
            </p>
          </v-card-text>
        </v-card>
      </v-tab-item>
      <v-tab-item>
        <v-card flat>
          <v-card-text class="pa-0 pa-sm-4 pt-1">
            <div v-if="admin" class="d-flex flex-column">
              <v-alert type="info" text color="cgreen">
                {{ statusAttrs(booking.status, booking).info_admin }}
              </v-alert>
            </div>
            <v-simple-table>
              <template v-slot:default>
                <tbody>
                  <tr>
                    <td>Érkezés napja</td>
                    <td>{{ formatDate(booking.arrival) }}</td>
                  </tr>
                  <tr>
                    <td>Távozás napja</td>
                    <td>{{ formatDate(booking.departure) }}</td>
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
                </tbody>
              </template>
            </v-simple-table>
          </v-card-text>
        </v-card>
      </v-tab-item>
      <v-tab-item>
        <v-card flat>
          <v-card-text class="pa-0 pa-sm-4">
            <v-timeline align-top dense>
              <v-timeline-item
                v-for="(transition, i) in booking.transitions"
                :key="i"
                small
                fill-dot
                color="cgreen"
              >
                <v-row class="pt-1">
                  <v-col class="col-12 col-sm-5 pb-1 pb-sm-3">
                    <strong>{{ formatDateTime(transition.created) }}</strong>
                    <p class="text-caption mb-0">
                      {{ transition.editor.email }}
                    </p>
                  </v-col>
                  <v-col class="col-12 col-sm-7 pt-0 pt-sm-3">
                    <strong>{{
                      statusAttrs(transition.newStatus, booking).status
                    }}</strong>
                    <p class="text-caption mb-1" readonly>
                      {{ transition.comment }}
                    </p>
                  </v-col>
                </v-row>
                </v-timeline-item
              >
            </v-timeline>
          </v-card-text>
        </v-card>
      </v-tab-item>
    </v-tabs>
  </div>
</template>

<script>
import BookingDataService from "../../services/bookingData.service";
import BookingService from "../../services/booking.service";
import MomentService from "../../services/moment.service";

export default {
  name: "CBookingTabs",
  props: ["booking", "admin"],
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
  computed: {},
  mounted() {},
};
</script>
