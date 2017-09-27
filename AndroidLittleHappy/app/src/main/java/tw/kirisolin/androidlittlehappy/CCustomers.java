package tw.kirisolin.androidlittlehappy;

/**
 * Created by kirisolin on 2017/9/23.
 */

public class CCustomers {
        private String id;
        private String name;
        private String email;
        private String password;



        public CCustomers(String id, String name, String email, String password) {
            this.id = id;
            this.name = name;
            this.email = email;
            this.password = password;
        }


        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
        public String getpassword() {
            return password;
        }

        public void setpassword(String password) {
            this.password = password;
        }

}
