<template>
  <div class="NotFound">
    <v-container>
      <v-layout row wrap>
        <v-flex xs12>
          <v-card height="400" color="transparent" flat>
            <div class="display-3 mt-10 text-h3 zabatana error--text">Az oldal nem található.</div>
            <div class="grey--text lighten-5">
              Az oldal amit keresel nem létezik és soha nem is létezett. 
            </div>
            <div class="paragraph-text mt-2">
              <br />
              <br />Menj vissza a kezdőoldalra vagy próbáld meg még egyszer elérni az oldalt.
            </div>
          </v-card>
        </v-flex>
      </v-layout>
    </v-container>
  </div>
</template>
<script>
export default {
  name: "CPageNotFound",
  data: () => ({}),
};
</script>
