console.log("opa")
console.log("opa")
console.log("opa")
console.log("opa")
function test1(){
    console.log("test1")
    fetch('http://localhost:8080/test', {
        method: "POST",
        headers: {
            Accept: "application/json",
            "Content-Type": "application/json"
        }
    });
}