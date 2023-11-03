<template>

    <div class="container-fluid text-start">
        <div class="row">
            <div class="col-2">
                <EditorMenu />
            </div>
            <div class="col bg-light">
                <div id="update-tag-div" class="p-5">
                    <div class="" role="alert" id="tag-updated-alert"></div>
                    <div class="">
                                                                                                    <h2>Update Tag</h2>
                                                                                                <hr />
                                                                                            </div>
                    <form @submit.prevent="updateTag">
                        <div class="row mb-3">
                            <label class="col-sm-2 col-form-label">Name</label>
                            <div class="col-sm-5">
                                <input class="form-control" type="text" placeholder="Name" required v-model="tag.name" autofocus />
                            </div>
                        </div>
                        <div class="row mb-3">
                            <label class="col-sm-2 col-form-label">Description</label>
                            <div class="col-sm-5">
                                <textarea class="form-control" rows="5" placeholder="Description" v-model="tag.description" required> </textarea>
                            </div>
                        </div>
                        <div class="row mb-3">
                            <div class="col-sm-2"></div>
                            <div class="col-sm-5">
                                <button class="btn btn-warning" type="submit">Update</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
    import axios from 'axios';
import EditorMenu from '../user/EditorMenu.vue';

    export default {
        name: 'UpdateTagComponent',
components: {
      EditorMenu,
    },
        data() {
            return {
                tag: {},
                tagId: '',
            }
        },
        created() {
                  this.tagId = this.$route.params.id;
                },
        mounted() {
            this.fetchTag(this.tagId);
        },
        methods: {
            async fetchTag(id) {
                try {
                    const response = await axios.get(`/newsbyte/tags/${id}`);
                    this.tag = response.data.tag;

                } catch (error) {
                    console.error('Error fetching tag options:', error);
                }
            },
            async updateTag() {
                try {
                    const response = await axios.put(`/newsbyte/tags/${this.tagId}?action=update`, this.tag);
                    console.log('tag updated successfully', response);
                    document.getElementById('tag-updated-alert').setAttribute("class", "alert alert-success");
                    document.getElementById('tag-updated-alert').innerHTML = 'Tag Updated Successfully!';
                    this.tagId = this.tag.name;
                    this.$router.push('/editor-dashboard/update-tag/'+this.tagId);
                } catch (error) {
                    console.error('Error in updated tag', error);
                }
            },
        }
    }
</script>