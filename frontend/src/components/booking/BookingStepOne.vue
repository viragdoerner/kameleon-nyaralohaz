<template>
  <v-card elevation="0">
    <v-card-text class="d-flex justify-space-between align-center col-12">
      <v-col class="col-4 d-flex flex-column">
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
        <v-divider class="my-5"></v-divider>
        <v-text-field
          :value="totalCost + ' Ft'"
          label="Fizetendő összeg"
          outlined
          color="cgreen"
          dense
          readonly
          class="mt-7"
          :hint="
            'Az ' +
            selectedApartment.name +
            ' költsége egy éjszakára ' +
            selectedApartment.price +
            ' Ft'
          "
          :persistent-hint="!!selectedApartment"
        ></v-text-field>
        <v-expand-transition>
          <div class="pt-7" v-if="dates.length > 0" caption>
            Érkezés napja: {{ arrival }}
          </div>
        </v-expand-transition>
        <v-expand-transition>
          <div v-if="dates.length == 2" caption>
            Távozás napja: {{ departure }}
          </div>
        </v-expand-transition>
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
    </v-card-text>
    <v-card-actions>
      <v-btn
        color="clightgreen"
        @click="finishStepOne"
        :disabled="dates.length != 2"
        class="white--text"
      >
        Tovább
      </v-btn>
      <v-btn text @click="cancelBooking"> Mégse </v-btn>
    </v-card-actions>
  </v-card>
</template>

<script>
import ApiService from "../../services/api.service";
import BookingService from "../../services/booking.service";
import MomentService from "../../services/moment.service";

export default {
  name: "CBookingStepOne",
  props: [],
  components: {},
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
