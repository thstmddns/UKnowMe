const HOST = 'http://localhost:8000/api/v1/'

const MOVIE = 'movies/'
const ACCOUNTS = 'accounts/'
const ARTICLES = 'articles/'
const COMMENTS = 'comments/'
const REVIEWS = 'reviews/'

export default {
  accounts : {
    login: () => HOST + ACCOUNTS + 'login/',
    logout: () => HOST + ACCOUNTS + 'logout/',
    signup: () => HOST + ACCOUNTS + 'signup/',
    currentUserInfo: () => HOST + ACCOUNTS + 'user/',
    profile: username => HOST + ACCOUNTS + 'profile/' + username,
    isAdmin: username => HOST + ACCOUNTS + 'isadmin/' + username,
  },
  articles: {
    articles: () => HOST + ARTICLES,
    article: articlePk => HOST + ARTICLES + `${articlePk}/`,
    likeArticle: articlePk => HOST + ARTICLES + `${articlePk}/` + 'like/',
    comments: articlePk => HOST + ARTICLES + `${articlePk}/` + COMMENTS,
    comment: (articlePk, commentPk) =>
      HOST + ARTICLES + `${articlePk}/` + COMMENTS + `${commentPk}/`,
  },
  movies: {
    movies: () => HOST + MOVIE,
    movie: moviePK => HOST + MOVIE + `${moviePK}/`,
    likeMoive: moviePK => HOST + MOVIE + `${moviePK}/` + 'like/',
    reviews: moviePK => HOST + MOVIE + `${moviePK}/` + REVIEWS,
    review: (moviePK, reviewPk) => HOST + MOVIE + `${moviePK}/` + REVIEWS + `${reviewPk}/`
  } 
}
