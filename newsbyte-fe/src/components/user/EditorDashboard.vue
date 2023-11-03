<template>

    <div class="container-fluid text-start">
        <div class="row">
            <div class="col-2">
                <EditorMenu />
            </div>
            <div class="col bg-light">
                <div class="p-5">
                    <h1>Welcome to Editor Panel!</h1>
                    <hr />
                </div>
                <div class="p-5">
                    <h1>Active Channels</h1>
                            <hr />
                            <div class="row">
                                <div class="col-2 m-1">
                                    <button class="btn btn-light fs-1" @click="this.$router.push('/editor-dashboard/news/region/National')">National</button>
                                </div>
                                <div class="col">
                                    <div class="row">
                                        <button class="col-2 m-1 btn btn-light border-secondary rounded d-flex align-items-center justify-content-evenly"
                                            v-for="newspaper in nationalNewspapers" :key="newspaper.name" @click="this.$router.push('/editor-dashboard/news/newspaper/'+newspaper.name)">
                                                <img class="img-fluid" :src="newspaper.logoUrl"
                                                   style="max-width: 100px; max-height: 100px;"  />
                                        </button>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-2 m-1">
                                    <button class="btn btn-light fs-1" @click="this.$router.push('/editor-dashboard/news/region/International')">International</button>
                                </div>
                                <div class="col">
                                    <div class="row">
                                        <button class="col-2 m-1 btn btn-light border border-secondary rounded d-flex align-items-center justify-content-evenly"
                                            v-for="newspaper in internationalNewspapers" :key="newspaper.name" @click="this.$router.push('/editor-dashboard/news/newspaper/'+newspaper.name)">
                                                <img class="img-fluid" :src="newspaper.logoUrl"
                                                    style="max-width: 100px; max-height: 100px;" />
                                        </button>
                                    </div>
                                </div>
                            </div>
                </div>
            </div>
        </div>
    </div>

</template>

<script>
import EditorMenu from './EditorMenu.vue';
  import axios from 'axios';
      export default {
          name: 'EditorDashboardComponent',
components: {
      EditorMenu,
    },
          data() {
              return {
                  newspapers: [],
              };
          },
          computed: {
              nationalNewspapers() {
                  return this.newspapers.filter(newspaper => {
                      return newspaper.type == 'National';
                  })
              },
              internationalNewspapers() {
                  return this.newspapers.filter(newspapers => {
                      return newspapers.type == 'International';
                  })
              }
          },
          mounted() {
              this.fetchNewspapers();
          },

          methods: {
              async fetchNewspapers() {
                  try {
                      const response = await axios.get('/newsbyte/newspapers');
                      this.newspapers = response.data.newspapers;

                  } catch (error) {
                      console.error('Error fetching newspapers:', error);
                  }
              },
          },
      };

</script>