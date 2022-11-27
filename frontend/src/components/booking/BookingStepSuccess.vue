<template>
  <v-card v-if="!!booking" elevation="0">
    <v-card-title
      h3
      class="cyellow--text text-h3 zabatana pa-0 pa-sm-4" id="break-word"
      >Szeretettel várjuk nyaralónkban
      {{ booking.user ? booking.user.firstname : "" }}!</v-card-title
    >
    <v-card-text class="pa-0 pa-sm-4">
      <div class="mb-0">Sikeresen rögzítettük foglalását!</div>
      <div class="mb-0">
        Elküldtünk minden szükséges tudnivalót erre az emailcímre:
        {{ booking.user ? booking.user.email : "" }}
      </div>
      <div class="pt-3">
        Tovább érdeklődni, esetleg a foglalást lemondani, módosítani az alábbi
        telefonszámon és emailcímen, illetve a weblapon is lehetséges.
      </div>
      <div class="pt-4 d-flex flex-wrap">
        <v-col class="col-12 col-sm-4 px-0 px-sm-3">
          <v-text-field
            label="Telefonszám"
            solo
            dense
            readonly
            value="+36303699697"
            prepend-icon="mdi-phone"
            color="clightgreen"
          ></v-text-field>
        </v-col>
        <v-col class="col-12 col-sm-4 px-0 px-sm-3">
          <v-text-field
            label="Telefonszám"
            solo
            dense
            readonly
            value="+36302460637"
            prepend-icon="mdi-phone"
            color="clightgreen"
          ></v-text-field>
        </v-col>
        <v-col class="col-12 col-sm-4 px-0 px-sm-3">
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
      </div>
      <div class="px-0 px-sm-3">
        A foglalás véglegesítéséhez
        <span class="overline corange--text">foglalót</span> kell fizetni. Ezt
        így meg így lehet megtenni, ide meg ide lehet utalni. A foglaló
        befizetésére egy hét áll rendelkezésre, utána a foglalást töröljük.
      </div>
      <!-- <div
        class="pt-3 px-0 px-sm-3 overline font-weight-black clightgreen--text"
      >
        Várjuk szeretettel nyaralónkban!
      </div> -->
      <div>
      <v-divider class="col-12 my-5 pb-0"></v-divider>
      </div>
      <div class="d-flex flex-wrap">
        <v-col class="col-12 col-sm-6 px-0 px-sm-3">
          <v-text-field
            label="Nyaraló címe:"
            value="Balatonszemes, Semmelweis utca 72/B"
            color="cgreen"
            readonly
          ></v-text-field
        ></v-col>
        <v-col class="col-12 col-sm-6 px-0 px-sm-3">
          <v-text-field
            label="Fizetendő összeg:"
            :value="getTotalPrice + ' Ft'"
            color="cgreen"
            readonly
          ></v-text-field
        ></v-col>
        <v-col class="col-12 col-sm-6 px-0 px-sm-3">
          <v-text-field
            label="Érkezés napja:"
            :value="arrival"
            color="cgreen"
            readonly
          ></v-text-field
        ></v-col>
        <v-col class="col-12 col-sm-6 px-0 px-sm-3">
          <v-text-field
            label="Távozás napja:"
            :value="departure"
            color="cgreen"
            readonly
          ></v-text-field
        ></v-col>
      </div>
    </v-card-text>
    <v-card-actions class="col-12 d-flex justify-end">
      <v-btn
        color="cyellow"
        to="/user-booking"
        class="white--text"
        :x-large="$vuetify.breakpoint.mobile"
      >
        OK!
      </v-btn>
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
    arrival() {
      return MomentService.formatDate(this.booking.arrival);
    },
    departure() {
      return MomentService.formatDate(this.booking.departure);
    },
    getTotalPrice() {
      if (!!!this.booking || !!!this.booking.apartment) return "";
      return BookingService.getTotalPriceForBooking(
        this.booking.arrival,
        this.booking.departure,
        this.booking.apartment.price
      );
    },
  },
  mounted() {},
};
</script>
<style>
#break-word {
  word-break: break-word;
}
</style>
