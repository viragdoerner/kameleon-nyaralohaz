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
            ><v-btn text class="overline cgreen--text" href="/booking"
              >ezen a linken</v-btn
            ></span
          >
          tudsz
        </div>
        <v-expansion-panels multiple>
          <v-expansion-panel v-for="(item, i) in active_bookings" :key="i">
            <v-expansion-panel-header>
              <template>
                <v-row no-gutters>
                  <v-col cols="1">
                    <v-icon :color="statusAttrs(item.status, item).color">
                      {{ statusAttrs(item.status, item)["icon"] }}
                    </v-icon>
                  </v-col>
                  <v-col cols="4" class="d-flex align-center">
                    <div class="overline">
                      {{ statusAttrs(item.status, item).title }}
                    </div>
                  </v-col>
                </v-row>
              </template>
            </v-expansion-panel-header>
            <v-expansion-panel-content>
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
                    <v-card-text>
                      <p>
                        {{ statusAttrs(item.status, item).info }}
                      </p>
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
                              <td>{{ formatDate(item.arrival) }}</td>
                            </tr>
                            <tr>
                              <td>Távozás napja</td>
                              <td>{{ formatDate(item.departure) }}</td>
                            </tr>
                            <tr>
                              <td>Kutyussal jövünk</td>
                              <td>{{ item.dogIncluded ? "Igen" : "Nem" }}</td>
                            </tr>
                            <tr>
                              <td>Lefoglalt apartman</td>
                              <td>{{ item.apartment.name }}</td>
                            </tr>
                            <tr>
                              <td>Foglalás teljes ára</td>
                              <td>{{ getTotalPrice(item) }} Ft</td>
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
                          v-for="(transition, i) in item.transitions"
                          :key="i"
                          small
                          fill-dot
                          color="cgreen"
                        >
                          <v-row class="pt-1">
                            <v-col cols="3">
                              <strong>{{
                                formatDateTime(transition.created)
                              }}</strong>
                            </v-col>
                            <v-col>
                              <strong>{{transition.newStatus}}</strong>
                            </v-col>
                          </v-row></v-timeline-item
                        >
                      </v-timeline>
                    </v-card-text>
                  </v-card>
                </v-tab-item>
              </v-tabs>
            </v-expansion-panel-content>
          </v-expansion-panel>
        </v-expansion-panels>
      </v-card-text>
      <v-card-title
        v-if="inactive_bookings.length > 0"
        class="cgreen--text text-h4"
      >
        Lejárt foglalások
      </v-card-title>
      <v-card-text>
        <v-expansion-panels multiple>
          <v-expansion-panel v-for="(item, i) in inactive_bookings" :key="i">
            <v-expansion-panel-header>
              <template>
                <v-row no-gutters>
                  <v-col cols="1">
                    <v-icon :color="statusAttrs(item.status, item).color">
                      {{ statusAttrs(item.status, item)["icon"] }}
                    </v-icon>
                  </v-col>
                  <v-col cols="4" class="d-flex align-center">
                    <div class="overline">
                      {{ statusAttrs(item.status, item).title }}
                    </div>
                  </v-col>
                </v-row>
              </template>
            </v-expansion-panel-header>
            <v-expansion-panel-content>
              {{ item.arrival }}
            </v-expansion-panel-content>
          </v-expansion-panel>
        </v-expansion-panels>
      </v-card-text>
    </v-card>
  </v-container>
</template>

<script>
import ApiService from "../services/api.service";
import BookingService from "../services/booking.service";
import MomentService from "../services/moment.service";

export default {
  name: "CUserBooking",
  components: {},
  data: () => ({
    dialogCancel: false,
    active_bookings: [],
    inactive_bookings: [],
    editedIndex: -1,
    editedItem: {
      email: "",
      lastname: 0,
      firstname: 0,
      phonenumber: 0,
    },
  }),

  watch: {
    dialogCancel(val) {
      val || this.closeCancel();
    },
  },

  created() {
    this.initialize();
  },

  mounted() {},
  computed: {},
  methods: {
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
      return BookingService.bookingStatusAttrs(status, booking);
    },
    cancelBooking(item) {
      this.editedIndex = this.bookings.indexOf(item);
      this.editedItem = Object.assign({}, item);
      this.dialogCancel = true;
    },

    cancelBookingConfirm() {
      this.closeCancel();
      ApiService.PUT("booking/cancel/" + this.editedItem.id)
        .then((response) => {
          this.users.splice(this.editedIndex, 1);
          this.$store.commit("showMessage", {
            active: true,
            color: "success", // You can create another actions for diferent color.
            message: "Foglalás sikeresen lemondva",
          });
        })
        .catch((error) => {
          this.$store.commit("showMessage", {
            active: true,
            color: "error",
            message:
              "Nem sikerült lemondani a foglalást. Próbáld újra, vagy mondd le telefonon vagy emailben!",
          });
        });
    },

    closeCancel() {
      this.dialogCancel = false;
      this.$nextTick(() => {
        this.editedItem = Object.assign({}, this.defaultItem);
        this.editedIndex = -1;
      });
    },
  },
};
</script>
