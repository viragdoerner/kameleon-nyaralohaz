<template>
  <v-container class="d-flex justify-center pb-10">
    <v-card class="col-12">
      <v-card-title class="cgreen--text text-h4">
        Aktív foglalásaid
      </v-card-title>
      <v-card-text>
          Jelenleg nincs foglalásod. Foglalni <span to="/booking" class="overline cgreen--text">ezen a linken</span> tudsz
        <v-expansion-panels multiple>
          <v-expansion-panel v-for="(item, i) in active_bookings" :key="i">
            <v-expansion-panel-header>
              <template>
                <v-row no-gutters v-if="item.status == 'TENTATIVE'">
                  <v-col cols="1">
                    <v-icon color="cyellow"> mdi-clock </v-icon>
                  </v-col>
                  <v-col cols="4" class="d-flex align-center">
                    <div class="overline">Foglalás alatt</div>
                  </v-col>
                </v-row>
                <v-row no-gutters v-if="item.status == 'BOOKED'">
                  <v-col cols="1">
                    <v-icon color="corange"> mdi-book </v-icon>
                  </v-col>
                  <v-col cols="4" class="d-flex align-center">
                    <div class="overline">Lefoglalva, foglalóra vár</div>
                  </v-col>
                </v-row>
                <v-row no-gutters v-if="item.status == 'PAID'">
                  <v-col cols="1">
                    <v-icon color="clightgreen">
                      mdi-checkbox-marked-circle
                    </v-icon>
                  </v-col>
                  <v-col cols="4" class="d-flex align-center">
                    <div class="overline">Lefoglalva, foglaló kifizetve</div>
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
      <v-card-title v-if="inactive_bookings.length>0" class="cgreen--text text-h4">
        Lejárt foglalások
      </v-card-title>
      <v-card-text>
        <v-expansion-panels multiple>
          <v-expansion-panel v-for="(item, i) in inactive_bookings" :key="i">
            <v-expansion-panel-header>
              <template>
                <v-row no-gutters v-if="item.status == 'DELETED'">
                  <v-col cols="1">
                    <v-icon color="red"> mdi-close-circle </v-icon>
                  </v-col>
                  <v-col cols="4" class="d-flex align-center">
                    <div class="overline">Törölve</div>
                  </v-col>
                </v-row>
                <v-row no-gutters v-if="item.status == 'OUTDATED'">
                  <v-col cols="1">
                    <v-icon color="purple"> mdi-timer-sand-empty </v-icon>
                  </v-col>
                  <v-col cols="4" class="d-flex align-center">
                    <div class="overline">Lejárt</div>
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
            color: "error", // You can create another actions for diferent color.
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
