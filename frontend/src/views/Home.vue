<template>
  <div>
    <c-header></c-header>
    <c-reserve-section
      :description="weekendhouse.description"
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
import axios from "axios";

export default {
  name: "Home",

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
      console.log(this.$store.state.baseURL);
      axios
        .get(this.$store.state.baseURL + "weekendhouse")
        .then((response) => {
          if (!response.data) throw "empty list";
          this.weekendhouse = response.data;
        })
        .catch((error) => {
          alert(error);
        });
    },
    getApartments() {
      axios
        .get(this.$store.state.baseURL + "apartment")
        .then((response) => {
          if (!response.data) throw "empty list";
          this.apartments = response.data;
        })
        .catch((error) => {
          alert(error);
        });
    },
    onAddProperty(p) {
      console.log("add" + p.name);
      this.weekendhouse.properties.push(p);
      console.log(this.weekendhouse.properties);
      this.saveWeekendhouse();
    },
    onDeleteProperty(p) {
      console.log("delete" + p.name);
      this.weekendhouse.properties= this.weekendhouse.properties.filter(function (property) {
        return property.id !== p.id;
      });
      console.log(this.weekendhouse.properties);
      this.saveWeekendhouse();
    },
    saveWeekendhouse() {
      axios
        .put(this.$store.state.baseURL + "weekendhouse", this.weekendhouse)
        .then((response) => {
          if (!response.data) throw "empty list";
          this.weekendhouse = response.data;
        })
        .catch((error) => {
          alert(error);
        });
    },
  },
};
</script>
