<template>
  <v-footer padless>
    <v-card flat tile width="100%" class="secondary text-center">
      <v-card-text>
        <transition-group class="footer-elements">
          <v-btn key="1"
            class="mx-4 footer-element"
            icon
            @click="expand_address = !expand_address"
          >
            <v-icon size="24px" color="green darken-3"> fa-home </v-icon>
          </v-btn>

          <p key="2"
            d-flex
            style="display: inline"
            v-show="expand_address"
            class="white--text footer-element"
          >
            8636, Balatonszemes, Semmelweis u.
          </p>

          <v-btn key="3"
            transition="slide-x-transition"
            class="mx-4 footer-element"
            icon
            @click="expand_phone = !expand_phone"
          >
            <v-icon d-flexsize="24px" color="purple darken-3">
              fa-phone
            </v-icon>
          </v-btn>
          <p key="4"
            d-flex
            style="display: inline"
            v-show="expand_phone"
            class="white--text footer-element"
          >
            8636, Balatonszemes, Semmelweis u.
          </p>
          <v-btn key="5" class="mx-4 footer-element" icon href="https://www.facebook.com/kameleonnyaralohaz" target="blank">
            <v-icon size="24px" color="blue darken-3"> fa-facebook </v-icon>
          </v-btn>
          <v-btn  key="6" class="mx-4 footer-element" icon href="mailto:mail.dorner.eva@gmail.com">
            <v-icon size="24px" color="red darken-3"> fa-envelope </v-icon>
          </v-btn>
        </transition-group>
      </v-card-text>

      <v-divider></v-divider>

      <v-card-text class="white--text">
        {{ new Date().getFullYear() }} — <strong>Kaméleon Nyaralóház</strong>
      </v-card-text>
    </v-card>
  </v-footer>
</template>

<script>
export default {
  name: "CFooter",
  data: () => ({
    expand_address: false,
    expand_phone: false,
  }),
};
</script>

<style>
.footer-element {
  transition: all 1s;
}
.footer-elements-enter, .footer-elements-leave-to,.footer-elements-leave, .footer-elements-enter-to {
  opacity: 0;
  transform: translateX(30px);
}

.footer-elements-leave-active, .footer-elements-enter-active {
  position: absolute;
}
</style>
