<template>
    <div class="container-fluid text-start">
        <div class="row">
            <div class="col-2">
                <EditorMenu />
            </div>
            <div class="col">
                <div id="news-div" class="p-5">
                    <div class="">
                           <h2>{{this.filterName}}</h2>
                           <hr />
                    </div>
                    <div style="max-height: 1000px, overflow:scroll;">
                    <table class="table table-hover table-secondary text-start" v-if="news.length > 0" >
                            <thead>
                                <tr>
                                    <th>Id</th>
                                    <th>News</th>
                                    <th>NewsPaper</th>
                                    <th>Tag</th>
                                    <th v-if="newsIrrespectiveOfTag">Assign New Tag</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr v-for="newsItem in news" :key="newsItem.detailsUrl"
                                    :class="{ 'table-danger': newsItem.tag.name === 'General', 'table-warning': newsItem.tag.name !== 'General' }">
                                    <td>{{newsItem.id}}</td>
                                    <td>
                                        <div class="card mb-3">
                                            <div class="row">
                                                <div class="col-md-2">
                                                    <a :href="newsItem.detailsUrl" target="_blank" :title="newsItem.title">
                                                        <img :src="newsItem.imageSource" class="img-fluid rounded-start"
                                                            :alt="newsItem.title" style="max-width: 150px; max-height: 100px;" />
                                                    </a>
                                                </div>
                                                <div class="col">
                                                    <div class="card-body">
                                                        <h6 class="card-title"><a :href=newsItem.detailsUrl target="_blank"
                                                                :title="newsItem.description" class="text-black">{{
                                                                newsItem.title }}</a></h6>
                                                        <p class="card-text"><small class="text-muted">{{
                                                                newsItem.publishedAt }}</small></p>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </td>
                                    <td>
                                        <a :href="newsItem.newspaper.siteUrl" target="_blank" :title="newsItem.newspaper.name">
                                            <img :src="newsItem.newspaper.logoUrl" class="img-fluid rounded-start" :alt="newsItem.title"
                                                style="max-width: 50px; max-height: 50px;" />
                                        </a>
                                    </td>
                                    <td>{{ newsItem.tag.name }}</td>
                                    <td v-if="newsIrrespectiveOfTag">
                                        <select class="form-select" aria-label="Default select example">
                                            <option disabled value="" selected>Select a Tag</option>
                                            <option v-for="tag in tags" :key="tag.id" :value="tag.name"
                                                @click="assignTag(newsItem.id, tag)">{{ tag.name }}</option>
                                        </select>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                        </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
    import axios from 'axios';
import EditorMenu from '../user/EditorMenu.vue';

    export default {
        name: 'EditorNewsComponent',
components: {
      EditorMenu,
    },
    data() {
                return {
                    tags: [],
                    news: [],
                    filterType: '',
                    filterName: '',
                    newsIrrespectiveOfTag: true,
                }
            },
        created() {
                          this.filterType = this.$route.params.filterType;
                          this.filterName = this.$route.params.filterName;
                          if(this.filterType=='tag'){
                                                          this.newsIrrespectiveOfTag= false;
                                                      }
                        },
                mounted() {
                    this.fetchNews(this.filterType, this.filterName);
                    this.fetchTags();
                },
                methods: {
                    async fetchNews(filterType, filterName) {
                        try {
                            const response = await axios.get('/newsbyte/news?filterType=' + filterType + '&filterName=' + filterName);
                            this.news = response.data.news;
                        } catch (error) {
                            console.error('Error fetching news:', error);
                        }
                    },
                    async fetchTags() {
                                 try {
                                   const response = await axios.get('/newsbyte/tags?filter=enabled');
                                   this.tags = response.data.tags.slice(1);

                                 } catch (error) {
                                   console.error('Error fetching tag options:', error);
                                 }
                               },
async assignTag(id, tag) {
                try {
                    const response = await axios.put('/newsbyte/news/assign-tag/'+id, tag);
                    const index = this.news.findIndex(item => item.id === response.data.news.id);
                    if (index !== -1) {
                        this.news[index].tag = response.data.news.tag;
                    }
                } catch (error) {
                    console.error('Error in updating tag', error);
                }
            },
                    }
    }
</script>