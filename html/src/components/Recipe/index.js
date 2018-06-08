import React from 'react';

export var data = [
  {
      "name": "Baked Salmon",
      "ingredients": ["Salmon", "Herbs"]
  },
  {
    "name": "Fried Eggs",
    "ingredients": ["Egg", "Olive Oil", "Salt"]
  }
];


const Recipe = ({name, ingredients}) =>
  <section>
    <h1>{name}</h1>
    <ul class="ingredients">
      {ingredients.map((ing, i) =>
        <li>{ing}</li>
      )}
    </ul>
  </section>

export const Menu = ({recipes}) =>
  <article>
    <header>Header Menu</header>
    <div class="recipes">
      {recipes.map((r, i) =>
        <Recipe key={i}  {...r}/>
      )}
    </div>
  </article>
