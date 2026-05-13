import axios from "axios";

const api = axios.create({
    baseURL: import.meta.env.BACKEND_API,
});

export default api;