<template>

<div class="bg-dark text-light p-5" style="min-height: 1000px;">

                                                    <button class="btn btn-danger m-2 mb-4 mt-4" @click="this.$router.push('/logout')">Logout</button>
                                                    <h4 class="ms-2">MENU</h4>
                                                    <button class="btn btn-outline-light m-1 w-100" @click="this.$router.push('/editor-dashboard')">Dashboard</button>
                                                    <button class="btn btn-outline-light m-1 w-100" @click="this.$router.push('/editor-dashboard/tags')">Manage Tags</button>
                                                    <button class="btn btn-outline-light m-1 w-100" @click="this.$router.push('/editor-dashboard/channels')">Manage Channels</button>
                                                    <button class="btn btn-outline-light m-1 w-100" @click="this.$router.push('/editor-dashboard/trends')">View Trends</button>
                                </div>
</template>


<script>

export default {
  name: 'EditorMenuComponent',


}

</script>