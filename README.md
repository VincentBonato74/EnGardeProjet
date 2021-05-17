#UGA-PROG5 Projet
Pour avoir une bonne gestion du projet et du dev de chacun, on va travailler avec des branches de developpement.



En bref, une branche permet de développer ses propres fonctionnalités, sans influer sur le travail des autres. Ça permet de gèrer efficacement les conflits et autres problèmes (éviter de supprimer le travail de chacun).

Cloner le projet :

git clone https://github.com/Frooztyes/UGA-PROG5.git


Faire un commit :

Voir si votre branche est derrière celle en ligne :

git status

Si votre branche est derrière celle en ligne :

git pull

Votre branche est à jour :

git add -A
git commit -m "message du commit"
git push


Créer une branche :

Crée une branche et s'y placer :

git checkout -b [nom de la branche]
git push origin [nom de la branche]

Vérifier que l'on est sur sa branche :

git branch -a
Affiche les branches et met une * devant la branche sur laquelle vous êtes.

Se mettre à jour par rapport à la branche main :

git rebase main [nom de la branche]

Merge votre branche dans la main :

Aller sur le site (lien)
Aller dans l'onglet "Pull requests"
Cliquer sur "New pull request" en vert
Choisir [nom de la branche] dans le bouton "compare"
Cliquer sur "Create pull request" en vert
Entrer une description et un commentaire (si besoin)
