<template>
  <v-card class="mb-12" elevation="0">
    <v-row class="d-flex justify-space-between align-center">
      <v-col class="col-4 d-flex flex-column">
        <v-combobox
          v-model="selectedApartment"
          :items="apartments"
          item-text="name"
          label="Apartman"
          solo
          color="cgreen"
          @change="dataChanged()"
        ></v-combobox>
        <v-checkbox
          v-model="dogIncluded"
          label="Hozzuk házi kedvencünket is"
          hint="Plusz költséggel nem jár, de korlátozhatja az elérhető dátumok számát."
          :persistent-hint="dogIncluded"
          color="cgreen"
          @change="dataChanged()"
        ></v-checkbox>
        <v-text-field
          :value="'Fizetendő összeg: ' + totalCost + ' Ft'"
          label="Solo"
          solo
          dense
          readonly
          class="mt-15"
          :hint="
            'Az ' +
            selectedApartment.name +
            ' költsége egy éjszakára ' +
            selectedApartment.price +
            ' Ft'
          "
          :persistent-hint="!!selectedApartment"
        ></v-text-field>
      </v-col>
      <v-col class="d-flex justify-start col-6">
        <v-date-picker
          v-model="dates"
          range
          color="cgreen"
          :disabled="!!!selectedApartment || disabled_dates === -1"
          :allowed-dates="allowedDates"
          show-adjacent-months
          full-width
          @click:date="dateClick"
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
    </v-row>
  </v-card>
</template>

<script>
import ApiService from "../../services/api.service";
import moment from 'moment';

export default {
  name: "CBookingStepOne",
  props: [],
  components: {},
  data: () => ({
    selectedApartment: "",
    apartments: [],
    dogIncluded: false,
    dates: [],
    disabled_dates: ["2022-04-09", "2022-04-08"],
    no_of_nights: 0,
    loading: false,
  }),
  methods: {
    getApartments() {
      ApiService.GET("apartment")
        .then((response) => {
          if (!response.data) throw "empty list";
          this.apartments = response.data;
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
      this.loading = true;
      if (!!this.selectedApartment) {
        const data = {
          apartmentId: this.selectedApartment.id,
          dogIncluded: this.dogIncluded,
        };
        ApiService.GET(
          "booking/disabled_dates/" + data.apartmentId + "/" + data.dogIncluded
        )
          .then((response) => {
            this.disabled_dates = [];
            this.disabled_dates = response.data;
            setTimeout(() => (this.loading = false), 1000);
          })
          .catch((error) => {
            this.disabled_dates = -1;
            this.loading = false;
            this.$store.commit("showMessage", {
              active: true,
              color: "error",
              message: "Hiba történt az elérhető dátumok lekérdezésénél",
            });
          });
      }
    },
    allowedDates(val) {
      return !this.disabled_dates.includes(val);
    },
    getDaysBetweenDates(startDate, endDate) {
      startDate = moment(startDate);
      endDate = moment(endDate);
      if(startDate >endDate){
        var temp = startDate;
        startDate = endDate;
        endDate = temp;
      }
      var now = startDate.clone(),
        dates = [];

      while (now.isSameOrBefore(endDate)) {
        dates.push(now.format("YYYY-MM-DD"));
        now.add(1, "days");
      }
      return dates;
    },
    dateClick(date) {
      if (this.dates.length > 1) {
        var dateList = this.getDaysBetweenDates(this.dates[0], this.dates[1]);
        const found = dateList.some((r) => this.disabled_dates.indexOf(r) >= 0);
        if (found) {
          this.dates = [];
        } else{
          this.no_of_nights =dateList.length-1;
        }
      }
    },
  },
  computed: {
    dateRangeText() {
      return this.dates.join(" ~ ");
    },
    totalCost() {
      const price = this.selectedApartment
        ? Number(this.selectedApartment.price)
        : 0;
      return price * Number(this.no_of_nights);
    },
  },
  mounted() {
    this.getApartments();
  },
};
</script>
