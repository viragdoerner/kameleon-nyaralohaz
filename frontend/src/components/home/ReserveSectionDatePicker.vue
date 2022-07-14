<template>
  <v-card
    class="
      pa-md-10 pa-4
      ma-md-10 ma-0
      mt-5 mt-md-10
      col-12 col-sm-8 col-md-6
      align-self-center
    "
  >
    <v-card-title class="text-h3 cgreen--text">Foglalás</v-card-title>
    <v-card-text
      >Nemsokára a weboldalon is lehet majd online foglalni! Addig ez az alábbi
      telefonszámok egyikén vagy emailben lehetséges.
    </v-card-text>
    <v-card-text
      class="d-flex justify-content-center align-items-center py-1"
      max-width="100%"
    >
      <v-chip class="mr-1 white--text" color="corange">+36302460637</v-chip>
      <v-chip class="mr-1 white--text" color="corange">+36304032377</v-chip>
    </v-card-text>
    <v-card-text class="d-flex justify-content-center pt-1" max-width="100%">
      <v-chip color="clightgreen white--text">mail.dorner.eva@gmail.com</v-chip>
    </v-card-text>
  </v-card>
</template>

<script>
import BookingService from "../../services/booking.service";
export default {
  name: "CReserveSectionDatePicker",
  data: () => ({
    menu: false,
    selectedApartment: "",
    dogIncluded: false,
    dates: [],
    disabled_dates: [],
    loading: false,
  }),
  methods: {
    dataChanged() {
      this.dates = [];
      this.loading = true;
      BookingService.getDisabledDates(this.selectedApartment, this.dogIncluded)
        .then((data) => {
          this.disabled_dates = data;
          this.loading = false;
        })
        .catch((data) => {
          this.disabled_dates = data;
          this.loading = false;
        });
    },
    allowedDates(val) {
      return BookingService.allowedDates(val, this.disabled_dates);
    },
    dateClick() {
      this.dates = BookingService.dateClick(this.dates, this.disabled_dates);
    },
    submit() {
      var bookingData = {
        selectedApartment: this.selectedApartment,
        dogIncluded: this.dogIncluded,
        disabled_dates: this.disabled_dates,
        apartments: this.apartments,
        dates: this.dates,
      };
      this.$store.commit("saveBookingData", bookingData);
      this.$router.push("/booking");
    },
  },
  computed: {
    dateRangeText() {
      return this.dates.join(" ~ ");
    },
  },
  mounted() {},
  props: ["apartments"],
};
</script>
<style  scoped>
.reserve-section {
  background-color: white;
}
</style>
