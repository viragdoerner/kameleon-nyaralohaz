<template>
<div>
  <c-header></c-header>
  <c-reserve-section></c-reserve-section>
  <c-icon-list></c-icon-list>
 <hello-world />
    <hello-world />
</div>
  
</template>

<script>
import CHeader from './Header.vue'
import CReserveSection from './ReserveSection.vue'
import CIconList from './IconList.vue'
import HelloWorld from '../components/HelloWorld'

  export default {
    name: 'Home',

    components: {
      CHeader,
      CReserveSection,
      CIconList,
      HelloWorld
    },
  }
</script>
