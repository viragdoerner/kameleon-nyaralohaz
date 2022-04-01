<template>
  <v-container class="d-flex justify-center pb-10 col-12 col-md-10">
    <v-stepper class="col-12" v-model="e1">
      <v-stepper-header>
        <v-stepper-step :complete="e1 > 1" step="1" editable color="cgreen">
          Dátum kiválasztása
        </v-stepper-step>

        <v-divider></v-divider>

        <v-stepper-step :complete="e1 > 2" step="2" editable color="cgreen">
          Adatok kitöltése
        </v-stepper-step>
      </v-stepper-header>

      <v-stepper-items>
        <v-stepper-content step="1">
          <booking-step-one></booking-step-one>
          <v-btn color="primary" @click="e1 = 2"> Tovább </v-btn>

          <v-btn text> Mégse </v-btn>
        </v-stepper-content>

        <v-stepper-content step="2">
          <booking-step-two></booking-step-two>

          <v-btn color="primary" @click="e1 = 2"> Tovább </v-btn>

          <v-btn text> Mégse </v-btn>
        </v-stepper-content>
      </v-stepper-items>
    </v-stepper>
  </v-container>
</template>

<script>
import BookingStepOne from "../components/booking/BookingStepOne.vue";
import BookingStepTwo from "../components/booking/BookingStepTwo.vue";
export default {
  name: "CBooking",
  props: [],
  components: { BookingStepOne,BookingStepTwo },
  data: () => ({
    e1: 1,
  }),
  methods: {},
};
</script>
