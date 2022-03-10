<template>
  <div>
    <v-row align="center" class="mx-0">
      {{$store.getters.loggedIn && $store.getters.getRole === 'Admin'}}
      <div v-if="$store.getters.loggedIn && $store.getters.getRole === 'Admin'" class="bars">
        <vue-file-toolbar-menu
          v-for="(content, index) in bars_content"
          :key="'bar-' + index"
          :content="content"
        />
      </div>
      {{$store.getters.loggedIn && $store.getters.getRole === 'Admin'}}
      <div
        id="mytext"
        ref="text"
        class="text"
        :contenteditable="$store.getters.loggedIn && $store.getters.getRole === 'Admin'"
        spellcheck="false"
        v-html="description"
      ></div>
    </v-row>
    {{$store.getters.loggedIn && $store.getters.getRole === 'Admin'}}
    <v-row v-if="$store.getters.loggedIn && $store.getters.getRole === 'Admin'" class="pt-5 mx-0" align="center">
      <v-btn elevation="2" color="cgreen" class="white--text" @click="saveText"
        >MENTÉS
      </v-btn>
    </v-row>
  </div>
</template>

<script>
import VueFileToolbarMenu from "vue-file-toolbar-menu";
export default {
  name: "CRichTextEditor",
  components: { VueFileToolbarMenu },
  data: () => ({color: "#206a5d",
    font: "Roboto",}),
  props: ["description"],
  methods: {
    focus_text() {
      this.$refs.text.focus();
      document.execCommand("selectAll", false, null);
      document.getSelection().collapseToEnd();
    },
    saveText() {
      const newDescription = document.getElementById("mytext").innerHTML;
      this.$emit("update-description", newDescription);
    },
  },
  computed: {
    bars_content() {
      return [
        [
          {
            icon: "format_align_left",
            title: "Align left",
            hotkey: this.isMacLike ? "shift+command+l" : "ctrl+shift+l",
            click: () => document.execCommand("justifyLeft"),
          },
          {
            icon: "format_align_center",
            title: "Align center",
            hotkey: this.isMacLike ? "shift+command+e" : "ctrl+shift+e",
            click: () => document.execCommand("justifyCenter"),
          },
          {
            icon: "format_align_right",
            title: "Align right",
            hotkey: this.isMacLike ? "shift+command+r" : "ctrl+shift+r",
            click: () => document.execCommand("justifyRight"),
          },
          {
            icon: "format_align_justify",
            title: "Justify content",
            hotkey: this.isMacLike ? "shift+command+j" : "ctrl+shift+j",
            click: () => document.execCommand("justifyFull"),
          },
          { is: "separator" },
          {
            icon: "format_bold",
            title: "Bold",
            hotkey: this.isMacLike ? "command+b" : "ctrl+b",
            click: () => document.execCommand("bold"),
          },
          {
            icon: "format_italic",
            title: "Italic",
            hotkey: this.isMacLike ? "command+i" : "ctrl+i",
            click: () => document.execCommand("italic"),
          },
          {
            icon: "format_underline",
            title: "Underline",
            hotkey: this.isMacLike ? "command+u" : "ctrl+u",
            click: () => document.execCommand("underline"),
          },
          {
            icon: "format_strikethrough",
            title: "Strike through",
            click: () => document.execCommand("strikethrough"),
          },
          /* {
            is: "button-color",
            type: "compact",
            menu_class: "align-center",
            stay_open: false,
            color: this.color,
            update_color: (new_color) => {
              this.color = new_color;
              document.execCommand("foreColor", false, new_color.hex8);
            },
          }, */
          { is: "separator" },
          {
            html:
              '<div class="ellipsis" style="width: 80px; font-size: 95%;">' +
              this.font +
              "</div>",
            title: "Font",
            chevron: true,
            menu: this.font_menu,
            menu_height: 200,
          },
          { is: "separator" },
          {
            icon: "format_list_numbered",
            title: "Numbered list",
            click: () => document.execCommand("insertOrderedList"),
          },
          {
            icon: "format_list_bulleted",
            title: "Bulleted list",
            click: () => document.execCommand("insertUnorderedList"),
          },
          { is: "separator" },
          {
            icon: "format_indent_increase",
            title: "Increase indent",
            click: () => document.execCommand("indent"),
          },
          {
            icon: "format_indent_decrease",
            title: "Decrease indent",
            click: () => document.execCommand("outdent"),
          },
          { is: "separator" },
          /* {
            html: "<b>H1</b>",
            title: "Header 1",
            click: () => document.execCommand("formatBlock", false, "<h1>"),
          },
          {
            html: "<b>H2</b>",
            title: "Header 2",
            click: () => document.execCommand("formatBlock", false, "<h2>"),
          },
          {
            html: "<b>H3</b>",
            title: "Header 3",
            click: () => document.execCommand("formatBlock", false, "<h3>"),
          }, */
          {
            icon: "format_clear",
            text: "Clear",
            title: "Clear format",
            click() {
              document.execCommand("removeFormat");
              document.execCommand("formatBlock", false, "<div>");
            },
          },
        ],
      ];
    },
    isMacLike: () => /(Mac|iPhone|iPod|iPad)/i.test(navigator.platform),
    font_menu() {
      return this.font_list.map((font) => {
        return {
          html:
            '<span class="ellipsis" style="font-family:' +
            font +
            '">' +
            font +
            "</span>",
          icon: this.theme != "default" && this.font == font ? "check" : false,
          active: this.font == font,
          height: 20,
          click: () => {
            document.execCommand("fontName", false, font);
            this.font = font;
          },
        };
      });
    },
    font_list: () => ["Roboto Thin", "Roboto Light", "Roboto"],
    is_touch_device: () =>
      "ontouchstart" in window || window.navigator.msMaxTouchPoints > 0,
  },
  mounted() {
    if (!this.is_touch_device) this.focus_text();
  }
};
</script>

<style>
</style>
