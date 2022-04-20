<template>
  <v-tabs vertical color="cgreen">
    <v-tab class="d-flex justify-start caption">
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

    <v-tab-item>
      <v-card flat>
        <v-card-text class="d-flex flex-column">
          <p>
            {{ statusAttrs(booking.status, booking).info }}
          </p>
          <v-tooltip bottom>
            <template v-slot:activator="{ on, attrs }">
              <v-btn
                v-bind="attrs"
                v-on="on"
                @click="cancelBooking()"
                elevation="2"
                small
                color="white"
                class="mt-auto align-self-end"
                ><v-icon left color="red"> fa-ban </v-icon>FOGLALÁS LEMONDÁSA
              </v-btn>
            </template>
            <span class="text-caption"><p class="mb-0">Meglévő foglalást bármikor lemondhatsz, </p> de a befizetett foglaló nem kerül visszafizetésre.</span>
          </v-tooltip>
        </v-card-text>
      </v-card>
    </v-tab-item>
    <v-tab-item>
      <v-card flat>
        <v-card-text>
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
                  <p class="text-caption mb-2">{{ transition.editor.email }}</p>
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
</template>

<script>
import BookingDataService from "../../services/bookingData.service";
import BookingService from "../../services/booking.service";
import MomentService from "../../services/moment.service";

export default {
  name: "CBookingTabs",
  props: ["booking"],
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
