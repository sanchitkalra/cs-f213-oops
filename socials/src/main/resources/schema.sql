CREATE TABLE IF NOT EXISTS UserDetails (
   id INT NOT NULL,
   name varchar(255) NOT NULL,
   email varchar(255) NOT NULL,
   PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS Account (
   email varchar(255) NOT NULL,
   password varchar(255) NOT NULL,
   PRIMARY KEY (email)
);

CREATE TABLE IF NOT EXISTS PostDetails {
    postId INT NOT NULL,
    postBody varchar(2000) NOT NULL,
    date timestamp NOT NULL,
    PRIMARY KEY (postId)
};

CREATE TABLE IF NOT EXISTS Comment {
    id INT NOT NULL,
    commentBody varchar(2000) NOT NULL,
    commentCreator
};