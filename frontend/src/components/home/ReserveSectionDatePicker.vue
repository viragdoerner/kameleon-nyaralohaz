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
    <v-card-text>
      <v-row>
        <v-combobox
          class="my-0"
          v-model="selectedApartment"
          :items="apartments"
          item-text="name"
          prepend-inner-icon="mdi-home"
          label="Apartman"
          solo
          :disabled="disable_apartment"
          color="cgreen"
          @change="dataChanged()"
        ></v-combobox>
      </v-row>
      <v-row>
        <v-menu
          ref="menu"
          v-model="menu"
          :close-on-content-click="false"
          :return-value.sync="dates"
          transition="scale-transition"
          offset-y
          min-width="auto"
        >
          <template v-slot:activator="{ on, attrs }">
            <v-text-field
              v-model="dates"
              :disabled="!!!selectedApartment"
              label="Date range"
              prepend-inner-icon="mdi-calendar"
              readonly
              solo
              v-bind="attrs"
              v-on="on"
              @click="dateClick"
            ></v-text-field>
          </template>
          <v-date-picker
            v-model="dates"
            range
            no-title
            scrollable
            color="cgreen"
            :disabled="!!!selectedApartment || disabled_dates === -1"
            :allowed-dates="allowedDates"
            show-adjacent-months
            full-width
            @input="dateClick"
          >
            <v-progress-linear
              :active="loading"
              :indeterminate="loading"
              absolute
              bottom
              color="cgreen"
            >
            </v-progress-linear>
            <v-spacer></v-spacer>
            <v-btn text color="primary" @click="menu = false"> Cancel </v-btn>
            <v-btn text color="primary" @click="$refs.menu.save(dates)">
              OK
            </v-btn>
          </v-date-picker>
        </v-menu>
      </v-row>

      <v-row>
        <v-checkbox
          class="my-0"
          v-model="dogIncluded"
          label="Kutyussal jövünk"
          hint="Plusz költséggel nem jár, de korlátozhatja az elérhető dátumok számát."
          :persistent-hint="dogIncluded"
          color="cgreen"
          @change="dataChanged()"
        ></v-checkbox>
      </v-row>
      <v-row>
        <v-btn
          elevation="2"
          x-large
          color="corange"
          class="white--text mt-3"
          @click="submit()"
          >FOGLALÁS
        </v-btn>
      </v-row>
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
    disable_apartment: false,
    loading: false,
  }),
  methods: {
    dataChanged() {
      this.dates = [];
      this.loading = true;
      BookingService.getDisabledDates(this.selectedApartment, this.dogIncluded)
        .then((data) => {
          this.disabled_dates = MomentService.removeDayOfDepartureFromBookingDates(data);
          this.loading = false;
        })
        .catch(() => {
          this.$store.commit("showMessage", {
            active: true,
            color: "error",
            message: "Hiba történt az elérhető dátumok betöltésénél",
          });
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
  mounted() {
    if(this.apartments.length==1){
      this.selectedApartment = this.apartments[0];
      this.disable_apartment = true;
    }
  },
  props: ["apartments"],
};
</script>
<style  scoped>
.reserve-section {
  background-color: white;
}
</style>
