<template>
  <v-card elevation="0">
    <v-card-text
      class="d-sm-flex flex-wrap justify-space-between align-center col-12 pa-0 pa-sm-4"
    >
      <v-col class="col-12 col-sm-6 col-md-4 d-flex flex-column">
        <v-combobox
          class="my-0"
          v-model="selectedApartment"
          :items="apartments"
          item-text="name"
          label="Apartman"
          solo
          color="cgreen"
          @change="dataChanged()"
        ></v-combobox>
        <v-checkbox
          class="my-0"
          v-model="dogIncluded"
          label="Kutyussal jövünk"
          hint="Plusz költséggel nem jár, de korlátozhatja az elérhető dátumok számát."
          :persistent-hint="dogIncluded"
          color="cgreen"
          @change="dataChanged()"
        ></v-checkbox>
        <booking-step-one-info
          class="non-mobile-view"
          v-if="!$vuetify.breakpoint.mobile"
          :dates="dates"
          :selectedApartment="selectedApartment"
          :arrival="arrival"
          :departure="departure"
          :totalCost="totalCost"
        ></booking-step-one-info>
      </v-col>
      <v-col class="d-flex justify-start col-12 col-sm-6">
        <v-date-picker
          v-model="dates"
          range
          color="cgreen"
          :disabled="!!!selectedApartment || disabled_dates === -1"
          :allowed-dates="allowedDates"
          show-adjacent-months
          full-width
          @click:date="dateClick"
          :first-day-of-week="1"
        >
          <v-progress-linear
            :active="loading"
            :indeterminate="loading"
            absolute
            bottom
            color="cgreen"
          >
          </v-progress-linear
        ></v-date-picker>
      </v-col>
      <v-col
        class="d-flex justify-start col-12 pt-0 mt-0"
        v-if="$vuetify.breakpoint.mobile"
      >
        <booking-step-one-info
          :dates="dates"
          :selectedApartment="selectedApartment"
          :arrival="arrival"
          :departure="departure"
          :totalCost="totalCost"
        ></booking-step-one-info>
      </v-col>
    </v-card-text>
    <v-card-actions class="d-flex justify-end">
      <v-btn
        color="clightgreen"
        @click="finishStepOne"
        :disabled="dates.length != 2"
        class="white--text"
        :x-large="$vuetify.breakpoint.mobile"
      >
        Tovább
      </v-btn>
      <v-btn text @click="cancelBooking" :x-large="$vuetify.breakpoint.mobile">
        Mégse
      </v-btn>
    </v-card-actions>
  </v-card>
</template>

<script>
import ApiService from "../../services/api.service";
import BookingService from "../../services/booking.service";
import MomentService from "../../services/moment.service";
import BookingStepOneInfo from "./BookingStepOneInfo.vue";

export default {
  name: "CBookingStepOne",
  props: [],
  components: { BookingStepOneInfo },
  data: () => ({
    selectedApartment: "",
    apartments: [],
    dogIncluded: false,
    dates: [],
    disabled_dates: [],
    loading: false,
  }),
  methods: {
    getApartments() {
      ApiService.GET("apartment")
        .then((response) => {
          this.apartments = response.data;
          this.selectedApartment = this.apartments[0];
          if (this.$store.getters.isApartmentSelected) {
            var selectedId = this.$store.getters.getSelectedApartmentId;
            this.$store.commit("selectApartmentId", null);
            this.apartments.forEach((element) => {
              if (element.id == selectedId) {
                this.selectedApartment = element;
              }
            });
          }
          this.dataChanged();
        })
        .catch((error) => {
          this.$store.commit("showMessage", {
            active: true,
            color: "error",
            message: "Hiba történt az apartmanok adatainak lekérésénél",
          });
        });
    },
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
      return MomentService.allowedDates(val, this.disabled_dates);
    },
    dateClick(date) {
      this.dates = BookingService.dateClick(this.dates, this.disabled_dates);
    },
    finishStepOne() {
      var b = {
        apartmentId: this.selectedApartment.id,
        dogIncluded: this.dogIncluded,
        arrival: this.dates[0],
        departure: this.dates[1],
      };
      this.$emit("next-step", b);
    },
    cancelBooking() {
      this.$router.push("/");
    },
  },
  computed: {
    dateRangeText() {
      return this.dates.join(" ~ ");
    },
    totalCost() {
      if (!!!this.selectedApartment || !this.dates.length > 1) return "";
      return BookingService.getTotalPriceForBooking(
        this.dates[0],
        this.dates[1],
        this.selectedApartment.price
      );
    },
    arrival() {
      return MomentService.formatDate(this.dates[0]);
    },
    departure() {
      return MomentService.formatDate(this.dates[1]);
    },
  },
  mounted() {
    if (this.$store.getters.isAvailableBookingData) {
      var bookingData = this.$store.getters.getBookingData;
      this.$store.commit("saveBookingData", {});
      this.dogIncluded = bookingData.dogIncluded;
      this.dates = bookingData.dates;
      this.selectedApartment = bookingData.selectedApartment;
      this.apartments = bookingData.apartments;
      this.disabled_dates = bookingData.disabled_dates;
    } else {
      this.getApartments();
    }
  },
};
</script>
