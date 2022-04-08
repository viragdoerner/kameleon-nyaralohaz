<template>
  <v-card elevation="0">
    <v-card-title
      >Szeretettel várjuk nyaralónkban
      {{ booking.user.firstname }}!</v-card-title
    >
    <v-card-text>
      <v-row>
        <p class="mb-0">Sikeresen rögzítettük foglalását!</p>
        <p class="mb-0">
          Elküldtünk minden szükséges tudnivalót erre az emailcímre:
          {{ booking.user.email }}
        </p>
        <p>
          Tovább érdeklődni, esetleg a foglalást lemondani, módosítani az alábbi
          telefonszámon és emailcímen lehetséges. Várjuk szeretettel
          nyaralónkban!
        </p>
      </v-row>
      <v-row>
        <v-col>
          <v-text-field
            label="Telefonszám"
            solo
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
            readonly
            value="kameleonnyaralohaz@gmail.com"
            prepend-icon="mdi-email"
            color="clightgreen"
          ></v-text-field
        ></v-col>
      </v-row>
      <v-divider class="my-5"></v-divider>
      <v-row>
        <v-col cols="4">
          <v-subheader>Cím:</v-subheader>
        </v-col>
        <v-col cols="8">
          <v-text-field
            readonly
            value="Balatonszemes, Semmelweis utca 72/B"
            prepend-icon="mdi-location"
            color="cgreen"
          ></v-text-field>
        </v-col>
      </v-row>
      <v-row>
        <v-col cols="4">
          <v-subheader>Dátum:</v-subheader>
        </v-col>
        <v-col cols="8">
          <v-text-field
            readonly
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
            :value="getTotalPrice"
            prepend-icon="mdi-location"
            color="cgreen"
          ></v-text-field>
        </v-col>
      </v-row>
      <v-row>
        <div>
          A foglalás véglegesítéséhez foglalót kell fizetni. Ezt így meg így
          lehet megtenni, ide meg ide lehet utalni. A foglaló befizetésére egy
          hét áll rendelkezésre, utána a foglalást töröljük.
        </div>
      </v-row>
    </v-card-text>
    <v-card-actions>
      <v-btn color="cyellow" to="/" class="white--text"> OK! </v-btn>
    </v-card-actions>
  </v-card>
</template>

<script>
import moment from "moment";
export default {
  name: "CBookingStepSuccess",
  props: ["booking"],
  data: () => ({}),
  methods: {
    getDaysBetweenDates(startDate, endDate) {
      startDate = moment(startDate);
      endDate = moment(endDate);
      var now = startDate.clone(),
        dates = [];

      while (now.isSameOrBefore(endDate)) {
        dates.push(now.format("YYYY-MM-DD"));
        now.add(1, "days");
      }
      return dates;
    },
  },
  computed: {
    getDate() {
      return (
        moment(this.booking.arrival).locale("hu").format("LL") +
        " (" +
        moment(this.booking.arrival).locale("hu").format('dddd') +
        ") - " +
        moment(this.booking.departure).locale("hu").format("LL") +
        " (" +
        moment(this.booking.departure).locale("hu").format('dddd') +
        ")"
      );
    },
    getTotalPrice() {
      return this.booking.apartment.price *
        this.getDaysBetweenDates(this.booking.arrival, this.booking.departure)
    },
  },
  mounted() {console.log(this.booking)},
};
</script>
