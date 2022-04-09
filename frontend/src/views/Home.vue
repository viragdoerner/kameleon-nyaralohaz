<template>
  <div>
    <c-header></c-header>
    <c-reserve-section
      :description="weekendhouse.description" :apartments="apartments"
      v-on:update-description="onUpdateDescription"
    ></c-reserve-section>
    <c-icon-list
      :properties="weekendhouse.properties"
      v-on:add-property="onAddProperty"
      v-on:delete-property="onDeleteProperty"
    ></c-icon-list>
    <c-reserve-apartment-section
      v-for="(apartment, index) in apartments"
      v-bind:key="apartment.id"
      v-bind:index="index"
      v-bind:apartment="apartment"
    ></c-reserve-apartment-section>
  </div>
</template>

<script>
import CHeader from "../components/home/Header.vue";
import CReserveSection from "../components/home/ReserveSection.vue";
import CReserveApartmentSection from "../components/home/ReserveApartmentSection.vue";
import CIconList from "../components/home/IconList.vue";
import ApiService from "../services/api.service"

export default {
  name: "CHome",

  components: {
    CHeader,
    CReserveSection,
    CIconList,
    CReserveApartmentSection,
  },
  data: () => ({
    weekendhouse: {},
    apartments: [],
  }),
  mounted() {
    this.getWeekendhouse();
    this.getApartments();
  },
  methods: {
    getWeekendhouse() {
     ApiService.GET( "weekendhouse")
        .then((response) => {
          if (!response.data) throw "empty list";
          this.weekendhouse = response.data;
        })
        .catch((error) => {
          this.$store.commit("showMessage", {
            active: true,
            color: "error", // You can create another actions for diferent color.
            message: "Hiba történt a nyaraló adatainak lekérésénél",
          });
        });
    },
    getApartments() {
       ApiService.GET( "apartment")
        .then((response) => {
          if (!response.data) throw "empty list";
          this.apartments = response.data;
        })
        .catch((error) => {
          this.$store.commit("showMessage", {
            active: true,
            color: "error", // You can create another actions for diferent color.
            message: "Hiba történt az apartmanok adatainak lekérésénél",
          });
        });
    },
    onAddProperty(p) {
      var payload = JSON.parse(JSON.stringify(this.weekendhouse));
      payload.properties.push(p);
      this.saveWeekendhouse(payload);
    },
    onDeleteProperty(p) {
      var payload = JSON.parse(JSON.stringify(this.weekendhouse));
      payload.properties = payload.properties.filter(function (property) {
        return property.id !== p.id;
      });
      this.saveWeekendhouse(payload);
    },
    saveWeekendhouse(payload) {
       ApiService.PUT( "weekendhouse", payload)
        .then((response) => {
          if (!response.data) throw "empty list";
          this.weekendhouse = response.data;

          this.$store.commit("showMessage", {
            active: true,
            color: "cgreen", // You can create another actions for diferent color.
            message: "Sikeres mentés",
          });
        })
        .catch((error) => {
          this.$store.commit("showMessage", {
            active: true,
            color: "error", // You can create another actions for diferent color.
            message: "Sikertelen mentés",
          });
        });
    },
    onUpdateDescription(d) {
      var payload = JSON.parse(JSON.stringify(this.weekendhouse));
      payload.description = d;
      this.saveWeekendhouse(payload);
    },
  },
};
</script>
