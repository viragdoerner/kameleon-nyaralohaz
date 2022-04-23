<template>
  <div>
    <v-tabs vertical color="cgreen">
      <v-tab class="d-flex justify-start caption" v-if="!admin">
        <v-icon small left> mdi-android-messages </v-icon>
        Leírás
      </v-tab>
      <v-tab class="d-flex justify-start caption">
        <v-icon small left> mdi-information </v-icon>
        Részletek
      </v-tab>
      <v-tab class="d-flex justify-start caption">
        <v-icon small left> mdi-history </v-icon>
        Történet
      </v-tab>
      <v-btn v-if="booking.status == 'TENTATIVE' || booking.status == 'BOOKED'" large text class="d-flex justify-start caption" @click="hello">
       <v-icon small left color="success">
                    {{ statusAttrs(booking.status, booking).action_admin_icon_ok }}
                  </v-icon>
                  {{statusAttrs(booking.status, booking).action_admin_ok }}
      </v-btn>
      <v-btn v-if="booking.status == 'TENTATIVE' || booking.status == 'BOOKED'" large text class="d-flex justify-start caption" @click="hello">
        <v-icon small left color="corange">
                    {{ statusAttrs(booking.status, booking).action_admin_icon_cancel }}
                  </v-icon>
                  {{statusAttrs(booking.status, booking).action_admin_cancel }}
      </v-btn>
      <v-btn large text class="d-flex justify-start caption" @click="hello">
        <v-icon small left color="primary">
                    fa-pencil
                  </v-icon>
                  Állapot módosítás
      </v-btn>
      <v-btn large text class="d-flex justify-start caption" @click="hello">
        <v-icon small left color="error">
                    fa-trash
                  </v-icon>
                  Végleges törlés
      </v-btn>

      <v-tab-item v-if="!admin">
        <v-card flat>
          <v-card-text>
            <p v-if="!admin">
              {{ statusAttrs(booking.status, booking).info }}
            </p>
          </v-card-text>
        </v-card>
      </v-tab-item>
      <v-tab-item>
        <v-card flat>
          <v-card-text>
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
          <v-card-text>
            <v-timeline align-top dense>
              <v-timeline-item
                v-for="(transition, i) in booking.transitions"
                :key="i"
                small
                fill-dot
                color="cgreen"
              >
                <v-row class="pt-1">
                  <v-col cols="4">
                    <strong>{{ formatDateTime(transition.created) }}</strong>
                    <p class="text-caption mb-2">
                      {{ transition.editor.email }}
                    </p>
                  </v-col>
                  <v-col>
                    <strong>{{
                      statusAttrs(transition.newStatus, booking).status
                    }}</strong>
                    <p class="text-caption mb-2" readonly>
                      {{ transition.comment }}
                    </p>
                  </v-col>
                </v-row></v-timeline-item
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
