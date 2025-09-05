# Terraform Infrastructure for Citizen Registry

Αυτός ο φάκελος περιέχει τα Terraform configuration files για τη διαχείριση της RESTful υπηρεσίας **Citizen Registry** και της βάσης δεδομένων της στο AWS.

## 📂 Δομή Φακέλων

```
infra/
 ├── prep/        # Προετοιμασία: δημιουργία custom AMI από υπάρχον VM
 │    └── main.tf
 ├── exec/        # Εκτέλεση: διάταξη εφαρμογής + ΣΔΒΔ + load balancer
 │    ├── main.tf
 │    ├── variables.tf
 │    └── outputs.tf
 └── destroy/     # Διαγραφή: κατεδάφιση υποδομής
      └── main.tf
```

## 🔹 Προετοιμασία

Μεταβείτε στον φάκελο `prep/` και τρέξτε:

```bash
terraform init
terraform apply
```

Αυτό θα δημιουργήσει ένα custom **AMI** της εφαρμογής.

## 🔹 Εκτέλεση

Στον φάκελο `exec/`:

```bash
terraform init
terraform apply
```

Αυτό θα δημιουργήσει:

- 1 VM για το ΣΔΒΔ
- 3 VMs για την εφαρμογή Citizen Registry
- Load balancer που τα ενώνει
- Κανόνες πρόσβασης για app & DB

Οι βασικές παράμετροι (region, instance types, AMI IDs, ports) ορίζονται στο `variables.tf`.

## 🔹 Outputs

Μετά την εκτέλεση, τα εξής εμφανίζονται:

- Δημόσια IP του ΣΔΒΔ
- Δημόσιες IPs των VMs της εφαρμογής
- DNS όνομα του load balancer

## 🔹 Διαγραφή

Για κατεδάφιση της υποδομής:

```bash
terraform destroy
```

## ℹ️ Σημειώσεις

- Πριν τρέξετε, βεβαιωθείτε ότι έχετε ρυθμίσει σωστά τα **AWS credentials** (`~/.aws/credentials` ή environment variables).
- Τα AMI IDs (`app_ami`, `db_ami`) πρέπει να οριστούν χειροκίνητα πριν το `apply`.
- Μπορείτε να φτιάξετε αρχείο `terraform.tfvars` με δικές σας τιμές για πιο εύκολη διαχείριση.
