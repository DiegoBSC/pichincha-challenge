CREATE TABLE persons (
	id uuid NOT NULL,
	name varchar(150) NOT NULL,
	gender varchar(10) NOT NULL,
	age int NOT NULL,
	identification varchar(50) NOT NULL,
	address varchar(500) NOT NULL,
	telephone varchar(50) NOT NULL,
	CONSTRAINT persons_identification_uni UNIQUE (identification),
	CONSTRAINT persons_pkey PRIMARY KEY (id)
);

CREATE TABLE clients (
	id uuid NOT NULL,
	password varchar(150) NOT NULL,
	state bool NOT NULL,
	CONSTRAINT clients_pkey PRIMARY KEY (id),
	CONSTRAINT fk_client_person FOREIGN KEY (id) REFERENCES persons(id)
);

CREATE TABLE accounts (
	id uuid NOT NULL,
	account_number varchar(150) NOT NULL,
	account_type varchar(150) NOT NULL,
	initial_balance int8 NULL,
	state bool NOT NULL,
	client_id uuid NOT NULL,
	CONSTRAINT accounts_pkey PRIMARY KEY (id),
	CONSTRAINT fk_client_account FOREIGN KEY (client_id) REFERENCES clients(id)
);

CREATE TABLE movements (
	id uuid NOT NULL,
	movement_date timestamp NOT NULL,
	movement_type varchar(150) NOT NULL,
	movement_value int8 NULL,
	balance_initial int8 NULL,
	balance_available int8 NULL,
	account_id uuid NOT NULL,
	CONSTRAINT movements_pkey PRIMARY KEY (id),
	CONSTRAINT fk_movement_account FOREIGN KEY (account_id) REFERENCES accounts(id)
);