const HOST = 'http://uknowme.mooo.com:8080/'

const MEMBERS = 'member/'
const MACHINGS = 'maching/'
const FEATURES = 'feature/'
const ROOMS = 'room/'
const AVATARS = 'avatar/'
const NOTICES = 'notice/'

export default {
  members: {
    login: () => HOST + MEMBERS + 'login',
    logout: () => HOST + MEMBERS + 'logout',
    signup: () => HOST + MEMBERS + 'join',
    findId: () => HOST + MEMBERS + 'find/id',
    findPassword: () => HOST + MEMBERS + 'find/password',
    account: memberSeq => HOST + MEMBERS + `${memberSeq}`,
    idDuplicate: () => HOST + MEMBERS + 'check/id',
    nickNameDuplicate: () => HOST + MEMBERS + 'check/nickname',
    kakaoLogin: () => HOST + MEMBERS + 'kakao/callback',
    naverLogin: () => HOST + MEMBERS + 'naver/callback',
    kakao: () => HOST + MEMBERS + 'add/kakao',
    naver: () => HOST + MEMBERS + 'add/naver',
    block: () => HOST + MEMBERS + 'block',
    report: () => HOST + MEMBERS + 'report',
    reportInfo: reportSeq => HOST + MEMBERS + 'report/' + `${reportSeq}`,
  },
  machings: {
    oneToOne: memberSeq => HOST + MACHINGS + '1vs1/' + `${memberSeq}`,
    twoToTwo: memberSeq => HOST + MACHINGS + '2vs2/' + `${memberSeq}`,
  },
  features: {
    balance: () => HOST + FEATURES + 'balance',
    balanceInfo: balanceSeq => HOST + FEATURES + 'balance/' + `${balanceSeq}`,
    keyword: () => HOST + FEATURES + 'keyword',
    keywordInfo: keywordSeq => HOST + FEATURES + 'keyword/' + `${keywordSeq}`,
  },
  rooms: {
    like: memberSeq => HOST + ROOMS + 'like/' + `${memberSeq}`,
    exit: () => HOST + ROOMS + 'exit',
    entrance: () => HOST + ROOMS + 'entrance',
  },
  avatars: {
    avatar: () => HOST + 'avatar',
    avatarInfo: avatarSeq => HOST + AVATARS + `${avatarSeq}`,
  },
  notices: {
    notice: () => HOST + 'notice',
    noticeInfo: noticeSeq => HOST + NOTICES + `${noticeSeq}`,
  }
}