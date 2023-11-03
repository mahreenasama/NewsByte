<template>
  <footer class="text-center bg-dark text-light p-3 mt-3">
    <div class="mb-2">Copyright &copy; NewsByte @2023</div>
    <button class="btn btn-dark" @click="this.$router.push('/login')">Login as Editor</button>
  </footer>
</template>

<script>
export default {
  name: 'FooterComponent',
};
</script>
