CREATE TABLE "users" (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    user_file_id BIGINT NOT NULL,
    name VARCHAR(255) NOT NULL
    );

CREATE TABLE "orders" (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    order_file_id BIGINT NOT NULL,
    date DATE NOT NULL,
    user_id UUID,
    FOREIGN KEY (user_id) REFERENCES "users" (id) ON DELETE CASCADE
    );

CREATE TABLE "products" (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    product_file_id BIGINT NOT NULL,
    value DECIMAL(15,2) NOT NULL,
    order_id UUID,
    FOREIGN KEY (order_id) REFERENCES "orders" (id) ON DELETE CASCADE
    );