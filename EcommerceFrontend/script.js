document.getElementById("submitBtn").addEventListener("click", async () => {
    // 1. Target and parse the values typed on the screen fields
    const uId = document.getElementById("userId").value;
    const pId = document.getElementById("productId").value;
    const qtyCount = document.getElementById("quantity").value;
    const outputBox = document.getElementById("statusOutput");

    outputBox.innerText = "Transmitting processing arrays...";

    // 2. Format a raw CSV text body payload string matches our Java parser split rules
    const customPayload = `${uId},${pId},${qtyCount}`;

    try {
        // 3. Issue asynchronous fetch command straight across to your independent Eclipse port 8080
        const requestStream = await fetch("http://localhost:8080/checkout", {
            method: "POST",
            headers: {
                "Content-Type": "text/plain"
            },
            body: customPayload
        });

        // 4. Capture the raw string response generated from OrderService.java and post it to view
        const apiResponseText = await requestStream.text();
        outputBox.innerText = apiResponseText;

    } catch (error) {
        outputBox.innerText = "Network Fault: Target Java API cluster is unreachable.";
        console.error("Connection link failure logs:", error);
    }
});
