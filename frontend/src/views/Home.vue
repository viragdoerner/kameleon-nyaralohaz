<template>
  <div>
    <c-header></c-header>
    <c-reserve-section></c-reserve-section>
    <c-icon-list></c-icon-list>
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

export default {
  name: "Home",

  components: {
    CHeader,
    CReserveSection,
    CIconList,
    CReserveApartmentSection
  },
  data: () => ({
    apartments: [
      { name: "Földszinti apartman", id: 1 },
      { name: "Emeleti apartman", id: 2 },
      { name: "Harmadik apartman", id: 3 },
    ],
  }),
};
</script>
