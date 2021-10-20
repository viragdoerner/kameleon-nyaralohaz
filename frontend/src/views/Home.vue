<template>
  <div>
    <c-header></c-header>
    <c-reserve-section :description="weekendhouse.description"></c-reserve-section>
    <c-icon-list :properties="weekendhouse.properties"></c-icon-list>
    <c-reserve-apartment-section
      v-for="(apartment, index) in apartments"
      v-bind:key="apartment.id"
      v-bind:index="index"
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
    apartments: [
      { name: "Földszinti apartman", id: 1 },
      { name: "Emeleti apartman", id: 2 },
      { name: "Harmadik apartman", id: 3 },
    ],
  }),
  created() {},
  mounted() {
    console.log("Home component has been created!");
    this.getWeekendhouse();
  },
  methods: {
    getWeekendhouse() {
      axios
        .get("http://localhost:8080/weekendhouse")
        .then((response) => {
          if(!response.data)
            throw "empty list";
          this.weekendhouse = response.data;
        })
        .catch((error) => {
          alert(error);
        });
    },
  },
};
</script>
