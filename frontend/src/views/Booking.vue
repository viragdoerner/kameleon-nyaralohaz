<template>
  <v-container class="d-flex justify-center pb-10 col-12 col-md-10">
    <v-stepper class="col-12" v-model="currentStep">
      <v-stepper-header>
        <v-stepper-step
          :complete="currentStep > 1"
          step="1"
          color="cgreen"
        >
          Dátum kiválasztása
        </v-stepper-step>

        <v-divider></v-divider>

        <v-stepper-step
          :complete="currentStep > 2"
          step="2"
          color="cgreen"
        >
          Adatok kitöltése
        </v-stepper-step>
         <v-divider></v-divider>

        <v-stepper-step
          :complete="currentStep > 3"
          step="3"
          color="cgreen"
        >
          Véglegesítés
        </v-stepper-step>
      </v-stepper-header>

      <v-stepper-items>
        <v-stepper-content step="1">
          <booking-step-one v-on:next-step="currentStep++"></booking-step-one>
        </v-stepper-content>

        <v-stepper-content step="2">
          <booking-step-two v-on:next-step="currentStep++" v-on:back-step="currentStep--"></booking-step-two>
        </v-stepper-content>
        <v-stepper-content step="3">
          <booking-step-three v-on:next-step="bookApartment" v-on:back-step="currentStep--"></booking-step-three>
        </v-stepper-content>
      </v-stepper-items>
    </v-stepper>
  </v-container>
</template>

<script>
import BookingStepOne from "../components/booking/BookingStepOne.vue";
import BookingStepTwo from "../components/booking/BookingStepTwo.vue";
import BookingStepThree from "../components/booking/BookingStepThree.vue";
export default {
  name: "CBooking",
  props: [],
  components: { BookingStepOne, BookingStepTwo, BookingStepThree },
  data: () => ({
    currentStep: 1,
  }),
  methods: {
    bookApartment(){
      console.log("apartment booked!")
    }
  },
};
</script>
