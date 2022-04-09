<template>
  <v-container class="d-flex justify-center pb-10 col-12 col-sm-10 col-md-8">
    <v-stepper class="col-12" v-model="currentStep">
      <v-stepper-header>
        <v-stepper-step :complete="currentStep > 1" step="1" color="cgreen">
          Dátum kiválasztása
        </v-stepper-step>

        <v-divider></v-divider>

        <v-stepper-step :complete="currentStep > 2" step="2" color="cgreen">
          Adatok kitöltése
        </v-stepper-step>
        <v-divider></v-divider>

        <v-stepper-step :complete="currentStep > 3" step="3" color="cgreen">
          Véglegesítés
        </v-stepper-step>
        <v-divider v-if="successfulBooking"></v-divider>
        <v-stepper-step
          v-if="successfulBooking"
          :complete="currentStep > 4"
          step="4"
          color="cyellow"
        >
          Sikeres foglalás
        </v-stepper-step>
      </v-stepper-header>

      <v-stepper-items>
        <v-stepper-content step="1">
          <booking-step-one v-on:next-step="finishStepOne"></booking-step-one>
        </v-stepper-content>

        <v-stepper-content step="2">
          <booking-step-two
            v-on:next-step="finishStepTwo"
            v-on:back-step="currentStep--"
          ></booking-step-two>
        </v-stepper-content>
        <v-stepper-content step="3">
          <booking-step-three
            v-on:next-step="finishStepThree"
            v-on:back-step="currentStep--"
            :user="user"
            :booking="booking"
          ></booking-step-three>
        </v-stepper-content>
        <v-stepper-content step="4">
          <booking-step-success
            :booking="bookingResponse"
          ></booking-step-success>
        </v-stepper-content>
      </v-stepper-items>
    </v-stepper>
  </v-container>
</template>

<script>
import BookingStepOne from "../components/booking/BookingStepOne.vue";
import BookingStepTwo from "../components/booking/BookingStepTwo.vue";
import BookingStepThree from "../components/booking/BookingStepThree.vue";
import BookingStepSuccess from "../components/booking/BookingStepSuccess.vue";
export default {
  name: "CBooking",
  props: [],
  components: { BookingStepOne, BookingStepTwo, BookingStepThree, BookingStepSuccess },
  data: () => ({
    currentStep: 1,
    booking: {},
    user: {},
    successfulBooking: false,
    bookingResponse: {}
  }),
  methods: {
    finishStepOne(b) {
      this.currentStep++;
      this.booking = b;
    },
    finishStepTwo(u) {
      this.currentStep++;
      this.user = u;
    },
    finishStepThree(resp) {
      this.bookingResponse=resp;
      this.successfulBooking = true;
      this.currentStep = 4;
    },
  },
};
</script>
