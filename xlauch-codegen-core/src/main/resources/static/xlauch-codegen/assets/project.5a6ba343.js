import{s as t}from"./request.e149c638.js";const s=e=>t.get("/gen/project/"+e),o=()=>t.get("/gen/project/list"),c=e=>e.id?t.put("/gen/project",e):t.post("/gen/project",e);export{c as a,o as g,s as u};