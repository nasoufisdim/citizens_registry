output "db_instance_ip" {
  description = "Η δημόσια IP του ΣΔΒΔ"
  value       = aws_instance.db.public_ip
}

output "app_instance_ips" {
  description = "Λίστα με δημόσιες IPs των VMs που τρέχουν την εφαρμογή"
  value       = [for instance in aws_instance.app : instance.public_ip]
}

output "load_balancer_dns" {
  description = "Το DNS όνομα του Load Balancer"
  value       = aws_elb.app_lb.dns_name
}
