CREATE TABLE tests (
    id INT AUTO_INCREMENT NOT NULL,
    name VARCHAR(50) NOT NULL,
    description VARCHAR(255),

    PRIMARY KEY (id)
);

CREATE TABLE affiliates (
   id INT AUTO_INCREMENT NOT NULL,
   name VARCHAR(50) NOT NULL,
   age SMALLINT NOT NULL ,
   mail VARCHAR(30) NOT NULL ,

   PRIMARY KEY (id)
);

CREATE TABLE appointments (
   id INT AUTO_INCREMENT NOT NULL,
   date DATE NOT NULL,
   hour DATETIME NOT NULL,
   id_test INT NOT NULL,
   id_affiliate INT NOT NULL,

   FOREIGN KEY (id_test) REFERENCES tests(id),
   FOREIGN KEY (id_affiliate) REFERENCES affiliates(id),
   PRIMARY KEY (id)
);