const output = document.getElementById("output");
const messageBox = document.getElementById("messageBox");

document.addEventListener("DOMContentLoaded", () => {
    setupTabs();
    setupCustomerForm();

    document.getElementById("showCustomersBtn").addEventListener("click", loadCustomers);
    document.getElementById("showProductsBtn").addEventListener("click", loadProducts);
    document.getElementById("showOrdersBtn").addEventListener("click", loadOrders);
    document.getElementById("showPaymentsBtn").addEventListener("click", loadPayments);
    document.getElementById("showOrderItemsBtn").addEventListener("click", loadOrderItems);
});

function setupTabs() {
    const tabButtons = document.querySelectorAll(".tab-btn");
    const tabPanels = document.querySelectorAll(".tab-panel");

    tabButtons.forEach(button => {
        button.addEventListener("click", () => {
            const tabName = button.dataset.tab;

            tabButtons.forEach(btn => btn.classList.remove("active"));
            tabPanels.forEach(panel => panel.classList.remove("active"));

            button.classList.add("active");
            document.getElementById(`${tabName}-panel`).classList.add("active");

            clearMessage();
        });
    });
}

function setupCustomerForm() {
    const customerForm = document.getElementById("customerForm");

    customerForm.addEventListener("submit", async (e) => {
        e.preventDefault();

        const customerData = {
            firstName: document.getElementById("firstName").value.trim(),
            lastName: document.getElementById("lastName").value.trim(),
            phone: document.getElementById("phone").value.trim(),
            email: document.getElementById("email").value.trim(),
            address: document.getElementById("address").value.trim()
        };

        try {
            const response = await fetch("/api/customers", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(customerData)
            });

            if (!response.ok) {
                throw new Error("Failed to add customer.");
            }

            const savedCustomer = await response.json();

            showMessage(`Customer added successfully with ID: ${savedCustomer.customerId}`, "success");
            customerForm.reset();
            loadCustomers();
        } catch (error) {
            showMessage(error.message || "Something went wrong while adding customer.", "error");
        }
    });
}

async function loadCustomers() {
    await loadData("/api/customers", renderCustomers, "Customer data loaded successfully.");
}

async function loadProducts() {
    await loadData("/api/products", renderProducts, "Product data loaded successfully.");
}

async function loadOrders() {
    await loadData("/api/orders", renderOrders, "Order data loaded successfully.");
}

async function loadPayments() {
    await loadData("/api/payments", renderPayments, "Payment data loaded successfully.");
}

async function loadOrderItems() {
    await loadData("/api/order-items", renderOrderItems, "Order item data loaded successfully.");
}

async function loadData(url, renderer, successMessage) {
    try {
        output.innerHTML = `<div class="empty-state">Loading data...</div>`;

        const response = await fetch(url);

        if (!response.ok) {
            throw new Error("Failed to fetch data.");
        }

        const data = await response.json();
        renderer(data);
        showMessage(successMessage, "info");
    } catch (error) {
        output.innerHTML = "";
        showMessage(error.message || "Something went wrong while loading data.", "error");
    }
}

function renderCustomers(customers) {
    if (!customers || customers.length === 0) {
        output.innerHTML = `<div class="empty-state">No customers found.</div>`;
        return;
    }

    output.innerHTML = customers.map(customer => `
        <div class="data-card">
            <h3>${escapeHtml(customer.firstName)} ${escapeHtml(customer.lastName)}</h3>
            <p><strong>Customer ID:</strong> ${customer.customerId}</p>
            <p><strong>Phone:</strong> ${escapeHtml(customer.phone)}</p>
            <p><strong>Email:</strong> ${escapeHtml(customer.email)}</p>
            <p><strong>Address:</strong> ${escapeHtml(customer.address)}</p>
        </div>
    `).join("");
}

function renderProducts(products) {
    if (!products || products.length === 0) {
        output.innerHTML = `<div class="empty-state">No products found.</div>`;
        return;
    }

    output.innerHTML = products.map(product => `
        <div class="data-card">
            <h3>${escapeHtml(product.productName)}</h3>
            <p><strong>Product ID:</strong> ${product.productId}</p>
            <p><strong>Category:</strong> ${escapeHtml(product.category)}</p>
            <p><strong>Price:</strong> ${formatCurrency(product.price)}</p>
            <p><strong>Stock:</strong> ${product.stock}</p>
            <p><strong>Size:</strong> ${escapeHtml(product.size)}</p>
        </div>
    `).join("");
}

function renderOrders(orders) {
    if (!orders || orders.length === 0) {
        output.innerHTML = `<div class="empty-state">No orders found.</div>`;
        return;
    }

    output.innerHTML = orders.map(order => `
        <div class="data-card">
            <h3>Order #${order.orderId}</h3>
            <p><strong>Order Date:</strong> ${escapeHtml(order.orderDate)}</p>
            <p><strong>Total Amount:</strong> ${formatCurrency(order.totalAmount)}</p>
            <p><strong>Status:</strong> ${escapeHtml(order.status)}</p>
            <p><strong>Customer ID:</strong> ${order.customerId}</p>
            <p><strong>Customer Name:</strong> ${escapeHtml(order.customerName)}</p>
        </div>
    `).join("");
}

function renderPayments(payments) {
    if (!payments || payments.length === 0) {
        output.innerHTML = `<div class="empty-state">No payments found.</div>`;
        return;
    }

    output.innerHTML = payments.map(payment => `
        <div class="data-card">
            <h3>Payment #${payment.paymentId}</h3>
            <p><strong>Payment Date:</strong> ${escapeHtml(payment.paymentDate)}</p>
            <p><strong>Amount:</strong> ${formatCurrency(payment.amount)}</p>
            <p><strong>Method:</strong> ${escapeHtml(payment.paymentMethod)}</p>
            <p><strong>Status:</strong> ${escapeHtml(payment.paymentStatus)}</p>
            <p><strong>Order ID:</strong> ${payment.orderId}</p>
        </div>
    `).join("");
}

function renderOrderItems(orderItems) {
    if (!orderItems || orderItems.length === 0) {
        output.innerHTML = `<div class="empty-state">No order items found.</div>`;
        return;
    }

    output.innerHTML = orderItems.map(item => `
        <div class="data-card">
            <h3>${escapeHtml(item.productName)}</h3>
            <p><strong>Order Item ID:</strong> ${item.orderItemId}</p>
            <p><strong>Order ID:</strong> ${item.orderId}</p>
            <p><strong>Product ID:</strong> ${item.productId}</p>
            <p><strong>Quantity:</strong> ${item.quantity}</p>
            <p><strong>Subtotal:</strong> ${formatCurrency(item.subtotal)}</p>
        </div>
    `).join("");
}

function showMessage(text, type) {
    messageBox.innerHTML = `<div class="message ${type}">${text}</div>`;
}

function clearMessage() {
    messageBox.innerHTML = "";
}

function formatCurrency(value) {
    const number = Number(value);
    if (Number.isNaN(number)) {
        return value;
    }
    return `$${number.toFixed(2)}`;
}

function escapeHtml(value) {
    if (value === null || value === undefined) {
        return "";
    }

    return String(value)
        .replace(/&/g, "&amp;")
        .replace(/</g, "&lt;")
        .replace(/>/g, "&gt;")
        .replace(/"/g, "&quot;")
        .replace(/'/g, "&#039;");
}