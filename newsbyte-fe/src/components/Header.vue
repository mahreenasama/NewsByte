<template>

<nav class="navbar navbar-expand-lg navbar-dark bg-dark p-3 mb-3">
    <div class="container-fluid">
        <a class="me-3" href="/home">
                            <img src="../assets/logo.png" class="img-fluid" alt="NewsByte" style="max-width: 70px; max-height: 70px;"/>
        </a>
<a class="navbar-brand" href="/home">NewsByte</a>

        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <li class="nav-item me-3">
                    <button class="btn btn-dark" >National</button>
                </li>
                <li class="nav-item me-3">
                    <button class="btn btn-dark" >International</button>
                </li>
            </ul>
            <div class="d-flex">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                            <li class="nav-item me-3" v-for="tag in tags" :key="tag.id">
                                <button class="btn btn-dark">{{ tag.name }}</button>
                            </li>
                        </ul>
            </div>
        </div>
    </div>
</nav>

</template>

<script>
import axios from 'axios';

export default {
  name: 'HeaderComponent',

  data() {
    return {
      tags: [],
      news: [],
    };
  },
   mounted() {
        // Fetch the tag options from the backend server when the component is mounted
        this.fetchTagOptions();
      },
  methods: {
   async fetchTagOptions() {
             try {
               const response = await axios.get('/newsbyte/tags?filter=enabled');
               this.tags = response.data.tags.slice(1);

             } catch (error) {
               console.error('Error fetching tag options:', error);
             }
           },

           async fetchNewsByFilter(filterType, filterName) {
                        try {
                          const response = await axios.get('/newsbyte/news?filterType='+filterType+'&filterName='+filterName);
                          this.news = response.data.news;

                        } catch (error) {
                          console.error('Error fetching news:', error);
                        }
                      },

  },
};
</script>
