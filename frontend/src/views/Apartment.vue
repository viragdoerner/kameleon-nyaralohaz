<template>
  <div>
    <c-description-section
      :data="apartment"
      v-on:update-apartment="onUpdateApartment"
    ></c-description-section>
    <c-gallery-section :apartment="apartment"></c-gallery-section>
    <c-icon-list
      :properties="apartment.properties"
      v-on:add-property="onAddProperty"
      v-on:delete-property="onDeleteProperty"
    ></c-icon-list>
  </div>
</template>

<script>
import CHeader from "../components/home/Header.vue";
import CDescriptionSection from "../components/apartment/DescriptionSection.vue";
import CGallerySection from "../components/apartment/GallerySection.vue";
import CIconList from "../components/home/IconList.vue";

export default {
  name: "CApartment",
  props: ["apartment"],
  components: {
    CHeader,
    CDescriptionSection,
    CGallerySection,
    CIconList,
  },
  data: () => ({}),
  mounted() {},
  methods: {
    onAddProperty(p) {
      var payload = JSON.parse(JSON.stringify(this.apartment));
      payload.properties.push(p);
      this.saveApartment(payload);
    },
    onDeleteProperty(p) {
      var payload = JSON.parse(JSON.stringify(this.apartment));
      payload.properties = payload.properties.filter(function (property) {
        return property.id !== p.id;
      });
      this.saveApartment(payload);
    },
    saveApartment(payload) {
      this.$http
        .put(this.$store.state.baseURL + "apartment", payload)
        .then((response) => {
          this.apartment = response.data;

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
    onUpdateApartment(payload) {
      //var  payload = JSON.parse(JSON.stringify(this.apartment));
      this.saveApartment(payload);
    },
    created() {
    },
    mounted() {
    },
  },
};
</script>
