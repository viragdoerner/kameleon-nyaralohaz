<template>
  <v-card v-if="!!booking" elevation="0">
    <v-card-title h3 class="cyellow--text text-h3 zabatana"
      >Szeretettel várjuk nyaralónkban
      {{ booking.user ? booking.user.firstname : "" }}!</v-card-title
    >
    <v-card-text>
      <div class="mb-0">Sikeresen rögzítettük foglalását!</div>
      <div class="mb-0">
        Elküldtünk minden szükséges tudnivalót erre az emailcímre:
        {{ booking.user ? booking.user.email : "" }}
      </div>
      <div class="pt-3">
        Tovább érdeklődni, esetleg a foglalást lemondani, módosítani az alábbi
        telefonszámon és emailcímen, illetve a weblapon is lehetséges.
      </div>
      <v-row class="pt-4">
        <v-col>
          <v-text-field
            label="Telefonszám"
            solo
            dense
            readonly
            value="+36303699697, +36302560637"
            prepend-icon="mdi-phone"
            color="clightgreen"
          ></v-text-field>
        </v-col>
        <v-col>
          <v-text-field
            label="Emailcím"
            solo
            dense
            readonly
            value="kameleonnyaralohaz@gmail.com"
            prepend-icon="mdi-email"
            color="clightgreen"
          ></v-text-field
        ></v-col>
      </v-row>
      <div>
        A foglalás véglegesítéséhez
        <span class="overline corange--text">foglalót</span> kell fizetni. Ezt
        így meg így lehet megtenni, ide meg ide lehet utalni. A foglaló
        befizetésére egy hét áll rendelkezésre, utána a foglalást töröljük.
      </div>
      <div class="pt-3 overline font-weight-black clightgreen--text">
        Várjuk szeretettel nyaralónkban!
      </div>
      <v-divider class="my-5"></v-divider>
      <v-row>
        <v-col cols="4">
          <v-subheader>Nyaraló címe:</v-subheader>
        </v-col>
        <v-col cols="8">
          <v-text-field
            readonly
            dense
            value="Balatonszemes, Semmelweis utca 72/B"
            prepend-icon="mdi-location"
            color="cgreen"
          ></v-text-field>
        </v-col>
      </v-row>
      <v-row>
        <v-col cols="4">
          <v-subheader>Érkezés és távozás napja:</v-subheader>
        </v-col>
        <v-col cols="8">
          <v-text-field
            readonly
            dense
            :value="getDate"
            prepend-icon="mdi-location"
            color="cgreen"
          ></v-text-field>
        </v-col>
      </v-row>
      <v-row>
        <v-col cols="4">
          <v-subheader>Fizetendő összeg:</v-subheader>
        </v-col>
        <v-col cols="8">
          <v-text-field
            readonly
            clear-icon=""
            dense
            :value="getTotalPrice + ' Ft'"
            prepend-icon="mdi-location"
            color="cgreen"
          ></v-text-field>
        </v-col>
      </v-row>
    </v-card-text>
    <v-card-actions>
      <v-btn color="cyellow" to="/user-booking" class="white--text"> OK! </v-btn>
    </v-card-actions>
  </v-card>
</template>

<script>
import BookingService from "../../services/booking.service";
import MomentService from "../../services/moment.service";
export default {
  name: "CBookingStepSuccess",
  props: ["booking"],
  data: () => ({}),
  methods: {},
  computed: {
    getDate() {
      return (
        MomentService.formatDate(this.booking.arrival) +
        " - " +
        MomentService.formatDate(this.booking.departure)
      );
    },
    getTotalPrice() {
      if (!!!this.booking || !!!this.booking.apartment) return "";
      return BookingService.getTotalPriceForBooking(this.booking.arrival, this.booking.departure, this.booking.apartment.price);
    },
  },
  mounted() {},
};
</script>
